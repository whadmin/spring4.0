package com.springdb.mybatisplus.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

public class MpGenerator {


	// 全局配置
	public static GlobalConfig getGlobalConfig() {
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir("D:\\project_alibaba\\spring4_star\\spring4.0\\src\\main\\java");
		//gc.setOutputDir("d:\\");
		gc.setFileOverride(true);
		gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
		gc.setEnableCache(true);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(true);// XML columList
		gc.setKotlin(false); // 是否生成 kotlin 代码
		gc.setAuthor("niyue");

		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sDao");
		gc.setServiceName("MP%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setControllerName("%sController");
		return gc;
	}
	
	// 数据源配置
	public static DataSourceConfig dataSourceConfig() {
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert() {
			// 自定义数据库表字段类型转换【可选】
			@Override
			public DbColumnType processTypeConvert(String fieldType) {
				System.out.println("转换类型：" + fieldType);
				// 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
				return super.processTypeConvert(fieldType);
			}
		});
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("");
		dsc.setUrl("jdbc:mysql://127.0.0.1:3306/mybatis-plus?characterEncoding=utf8");
		
		return dsc;
	}
	
	// 策略配置
	public static  StrategyConfig getStrategyConfig(){
		StrategyConfig strategy = new StrategyConfig();
		/** 设置实体策略 **/
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setEntityBuilderModel(true);
		strategy.setEntityColumnConstant(true);
		/** 设置乐观锁和逻辑删除 **/
		strategy.setVersionFieldName("version");
		strategy.setLogicDeleteFieldName("delete");
		/** 设置通用实体父类 **/
		strategy.setSuperMapperClass("com.springdb.mybatisplus.common.SuperMapper");
		strategy.setSuperControllerClass("com.springdb.mybatisplus.common.SuperController");
		strategy.setSuperEntityClass("com.springdb.mybatisplus.common.SuperEntity");
		//strategy.setSuperEntityColumns(new String[] { "delete", "version", "ctime", "utime" });
		return strategy;
	}
	
	public static PackageConfig getPackageConfig(){
		PackageConfig pc = new PackageConfig();
		pc.setModuleName("mybatisplus");
		pc.setParent("com.springdb");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        pc.setServiceImpl("service.impl");
        pc.setXml("mybatis.mapper");
		return pc;
	}
	
	public static InjectionConfig getInjectionConfig(){
		// 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("abc", this.getConfig().getGlobalConfig().getAuthor()
						+ "-mp");
				this.setMap(map);
			}
		};
		// 自定义 xxList.jsp 生成
		List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		focList.add(new FileOutConfig("/template/list.jsp.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				return "D://my_" + tableInfo.getEntityName() + ".jsp";
			}
		});
		cfg.setFileOutConfigList(focList);

		// 调整 xml 生成目录演示
		focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return "/develop/code/xml/" + tableInfo.getEntityName()
						+ ".xml";
			}
		});
		cfg.setFileOutConfigList(focList);
		return cfg;
	}
	
	public static void main(String[] args) {
		AutoGenerator mpg = new AutoGenerator();
		// 设置模板引擎
		mpg.setTemplateEngine(new VelocityTemplateEngine());
		// 设置全局配置
		mpg.setGlobalConfig(getGlobalConfig());
		// 设置数据源配置
		mpg.setDataSource(dataSourceConfig());
		// 策略配置
		mpg.setStrategy(getStrategyConfig());
		// 包配置
		mpg.setPackageInfo(getPackageConfig());
		// 执行生成
		mpg.execute();
	}
}