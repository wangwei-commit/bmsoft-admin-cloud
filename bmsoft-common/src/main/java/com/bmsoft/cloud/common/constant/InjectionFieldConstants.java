package com.bmsoft.cloud.common.constant;

/**
 * 仅仅用于记录 RemoteField 注解相关的 接口和方法名称
 * <p>
 * 切记，该类下的接口和方法，一定要自己手动创建，否则会注入失败
 *
 * @author bmsoft
 * @date 2020年01月20日11:16:37
 */
public abstract class InjectionFieldConstants {

    /**
     * 数据字典项 feign查询类 切记，一定要在 DictionaryItemApi 上面添加属性：
     * qualifier="dictionaryItemApi"
     * <p>
     * 如： @FeignClient(name = "${bmsoft.feign.authority-server:bmsoft-authority-server}", path = "dictionaryItem",
     * qualifier = "dictionaryItemApi", fallback = DictionaryItemApiFallback.class)
     */
    public static final String DICTIONARY_ITEM_FEIGN_CLASS = "dictionaryItemApi";
    /**
     * 数据字典项 service查询类
     */
    public static final String DICTIONARY_ITEM_CLASS = "dictionaryItemServiceImpl";

    /**
     * 数据字典项 api查询方法
     */
    public static final String DICTIONARY_ITEM_METHOD = "findDictionaryItem";

    /**
     * 组织 service查询类
     */
    public static final String ORG_ID_CLASS = "orgServiceImpl";
    /**
     * 组织 feign查询类
     */
    public static final String ORG_ID_FEIGN_CLASS = "orgApi";

    /**
     * 组织 查询方法
     */
    public static final String ORG_ID_METHOD = "findOrgByIds";
    public static final String ORG_ID_NAME_METHOD = "findOrgNameByIds";


    /**
     * 用户 service查询类
     */
    public static final String USER_ID_CLASS = "userServiceImpl";
    /**
     * 用户 feign查询类
     */
    public static final String USER_ID_FEIGN_CLASS = "userApi";

    /**
     * 用户 查询方法
     */
    public static final String USER_ID_METHOD = "findUserByIds";
    public static final String USER_ID_NAME_METHOD = "findUserNameByIds";


    /**
     * 组织 service查询类
     */
    public static final String STATION_ID_CLASS = "stationServiceImpl";
    /**
     * 组织 feign查询类
     */
    public static final String STATION_ID_FEIGN_CLASS = "stationApi";

    /**
     * 组织 查询方法
     */
    public static final String STATION_ID_METHOD = "findStationByIds";
    public static final String STATION_ID_NAME_METHOD = "findStationNameByIds";

    /**
     * 凭证 查询方法
     */
	public static final String CERTIFICATE_ID_CLASS = "certificateServiceImpl";
	public static final String CERTIFICATE_ID_NAME_METHOD = "findNameByIds";

	/**
	 * 清单 查询方法
	 */
	public static final String INVENTORY_ID_CLASS = "inventoryServiceImpl";
	public static final String INVENTORY_ID_NAME_METHOD = "findNameByIds";


    /**
     * 运维场景 查询方法
     */
    public static final String SCENE_ID_CLASS = "sceneServiceImpl";
    public static final String SCENE_ID_NAME_METHOD = "findNameByIds";

    /**
     * 作业模板 查询方法
     */
    public static final String TEMPLATE_ID_CLASS = "templateServiceImpl";
    public static final String TEMPLATE_ID_NAME_METHOD = "findNameByIds";


}
