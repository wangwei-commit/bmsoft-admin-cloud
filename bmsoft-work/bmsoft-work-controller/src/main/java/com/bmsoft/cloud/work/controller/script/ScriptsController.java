package com.bmsoft.cloud.work.controller.script;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bmsoft.cloud.base.R;
import com.bmsoft.cloud.base.controller.SuperCacheController;
import com.bmsoft.cloud.base.request.PageParams;
import com.bmsoft.cloud.database.mybatis.conditions.query.QueryWrap;
import com.bmsoft.cloud.log.annotation.SysLog;
import com.bmsoft.cloud.security.annotation.PreAuth;
import com.bmsoft.cloud.work.dto.script.ScriptsPageDTO;
import com.bmsoft.cloud.work.dto.script.ScriptsSaveDTO;
import com.bmsoft.cloud.work.dto.script.ScriptsUpdateDTO;
import com.bmsoft.cloud.work.entity.scripts.Scripts;
import com.bmsoft.cloud.work.properties.TypeProperties;
import com.bmsoft.cloud.work.service.script.ScriptsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bmsoft.cloud.exception.code.ExceptionCode.BASE_VALID_PARAM;

;

/**
 * <p>
 * 前端控制器 剧本
 * </p>
 *
 * @author bmsoft
 * @date 2020-10-12
 */
@Validated
@RestController
@RequestMapping("/scripts")
@Api(value = "Scripts", tags = "剧本")
@PreAuth(replace = "scripts:")
public class ScriptsController extends
		SuperCacheController<ScriptsService, Long, Scripts, ScriptsPageDTO, ScriptsSaveDTO, ScriptsUpdateDTO> {

	@Resource
	private TypeProperties typeProperties;

	@SuppressWarnings("unchecked")
	@Override
	public R<Scripts> handlerSave(ScriptsSaveDTO model) {
		return   super.handlerSave(model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public R<Scripts> handlerUpdate(ScriptsUpdateDTO model) {
		return  super.handlerUpdate(model);
	}

	@Override
	public R<Boolean> handlerDelete(List<Long> longs) {

		return  super.handlerDelete(longs);
	}

	@Override
	public void handlerWrapper(QueryWrap<Scripts> wrapper, PageParams<ScriptsPageDTO> params) {
		super.handlerWrapper(wrapper, params);
	}

	@Override
	public void handlerResult(IPage<Scripts> page) {
		super.handlerResult(page);
	}


	/**
	 * 上传文件
	 *
	 * @param
	 * @return
	 * @author bmsoft
	 * @date 2019-05-06 16:28
	 */
	@ApiOperation(value = "脚本上传", notes = "脚本上传")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "file", value = "附件", dataType = "MultipartFile", allowMultiple = false, required = true),
	})
	@PostMapping(value = "/upload")
	@SysLog("上传附件")
	@PreAuth("hasPermit('{}upload')")
	public R<Scripts> upload(
			@RequestParam(value = "file") MultipartFile file) throws Exception {
		// 忽略路径字段,只处理文件类型
		if (file.isEmpty()) {
			return R.fail(BASE_VALID_PARAM.build("请求中必须至少包含一个有效文件"));
		}
		String originalFilename = file.getOriginalFilename();
		if(!originalFilename.contains("tar.gz")){
			return R.fail(BASE_VALID_PARAM.build("文件格式错误"));
		}
		originalFilename= originalFilename.replace(".tar.gz","");
		String methodUrl = typeProperties.getFileUrl() ;
		String rtnStr = "";
		InputStream inputStream = file.getInputStream();
		try {
			rtnStr = imageIdentify2(methodUrl, inputStream, originalFilename);
			JSONObject object = JSONObject.parseObject(rtnStr);

			if("2".equals( object.get("code"))){
				return R.fail(object.get("stdout")+"");
			}else if ("1".equals( object.get("code"))){
				Scripts script = new Scripts();
				JSONObject scripts =object.getJSONObject("roleInfo");
				if(scripts==null){
					return R.fail("剧本部署失败，没有返回剧本信息");
				}
				//tag
				JSONObject roleTags = object.getJSONObject("roleTags");
				//入参
				JSONObject inParam = object.getJSONObject("roleVars");
				/* 凭证 */
				script.setDepend(scripts.getString("dependencies").substring(1,scripts.getString("dependencies").length()-1));
				originalFilename = originalFilename.replace(".tar.gz","");
				script.setName(originalFilename);
				script.setAlias(originalFilename);
				script.setInParam(JSONObject.toJavaObject(inParam, Map.class));
				JSONObject jsonObject =scripts.getJSONObject("galaxy_info");
				script.setPlatform(jsonObject.getJSONArray("platforms").stream()
						.collect(Collectors.toMap(s -> JSONObject.parseObject(s+"").get("name")+"", s -> JSONObject.parseObject(s+"").get("versions"))));
				script.setDescription(jsonObject.getString("description"));
				script.setTags(roleTags.getString("vars").substring(1,roleTags.getString("vars").length()-1));
				return R.success(script);
			}
		} catch (Exception e) {
			return R.fail(e);
		} finally {
			inputStream.close();
		}

		return null;
	}

	private static String imageIdentify2(String methodUrl, InputStream inputStream, String fileName) {
		HttpURLConnection connection = null;
		OutputStream dataout = null;
		BufferedReader bf = null;
		String BOUNDARY = "----WebKitFormBoundary2NYA7hQkjRHg5WJk";
		String END_DATA = ("\r\n--" + BOUNDARY + "--\r\n");
		String BOUNDARY_PREFIX = "--";
		String NEW_LINE = "\r\n";
		String authString =  "some:one";
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		try {
			URL url = new URL(methodUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);
			connection.setDoOutput(true);// 设置连接输出流为true,默认false
			connection.setDoInput(true);// 设置连接输入流为true
			connection.setRequestMethod("POST");// 设置请求方式为post
			connection.setUseCaches(false);// post请求缓存设为false
			connection.setInstanceFollowRedirects(true);// 设置该HttpURLConnection实例是否自动执行重定向
			connection.setRequestProperty("connection", "Keep-Alive");// 连接复用
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
			connection.connect();// 建立连接

			dataout = new DataOutputStream(connection.getOutputStream());// 创建输入输出流,用于往连接里面输出携带的参数

			// 读取文件上传到服务器
			StringBuilder sb1 = new StringBuilder();
			sb1.append(BOUNDARY_PREFIX);
			sb1.append(BOUNDARY);
			sb1.append(NEW_LINE);
			sb1.append("Content-Disposition:form-data; name=\"rolePck\";filename=\"" + fileName +"\"");//文件名必须带后缀
			sb1.append(NEW_LINE);
			sb1.append("Content-Type:application/octet-stream");
			// 参数头设置完成后需要2个换行，才是内容
			sb1.append(NEW_LINE);
			sb1.append(NEW_LINE);

			dataout.write(sb1.toString().getBytes());
			//写入文件

			byte[] buffer = new byte[1024*2];
			int length = -1;
			while ((length = inputStream.read(buffer)) != -1){
				dataout.write(buffer,0,length);
			}
			dataout.write(END_DATA.getBytes("utf-8"));
			dataout.flush();
			dataout.close();

			bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 连接发起请求,处理服务器响应
			StringBuilder result = new StringBuilder(); // 用来存储响应数据
			// 循环读取流,若不到结尾处

			String bfs = null;
			while ((bfs=bf.readLine())!=null) {
				result.append(bfs).append(System.getProperty("line.separator"));
			}
			bf.close();
			connection.disconnect(); // 销毁连接
			System.out.println(result.toString().trim());
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
