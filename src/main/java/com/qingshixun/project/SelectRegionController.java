package com.qingshixun.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.qingshixun.project.dto.City;
import com.qingshixun.project.dto.District;
import com.qingshixun.project.dto.Province;

/**
 * 
 * @author toughChow
 * code is far away from bug with the animal protecting
 *  ┏┓　　  ┏┓
 * ┏┛  ┻━━━┛  ┻┓
 * ┃　　　　   ┃ 　
 * ┃　　━　     ┃
 * ┃　┳┛　┗┳   ┃
 * ┃　　　　    ┃
 * ┃　　　┻　  ┃
 * |  　　         |
 * ┗━┓　　　┏━┛
 * ┃　　　┃ 神兽保佑　　　　　　　　
 * ┃　　　┃ 代码无BUG！
 * ┃　　　┗━━━┓
 * ┃　 　　　　┣┓
 * ┃　 　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　┃┫┫
 * ┗┻┛　┗┻┛
 */
@Controller
@RequestMapping(value="select")
public class SelectRegionController {

	@RequestMapping("test")
	public String test(){
		return "index";
	}
	
	// 省份数据List
	private static List<Province> provinceList = new ArrayList<>();

	// 城市数据Map key：provinceCode,value:cities
	private static Map<String,List<City>> cityMap = new HashMap<>();
	
	// 区数据Map key：cityCode,value:districts
	private static Map<String,List<District>> districtMap = new HashMap<>();
	
	static{
		// 初始化省/市/区数据
		provinceList.add(new Province("hubei", "湖北"));
		provinceList.add(new Province("jilin", "吉林"));
		
		List<City> cities1 = new ArrayList<>();
		cities1.add(new City("wuhan","武汉市"));
		cities1.add(new City("suizhou","随州市"));
		cityMap.put("hubei", cities1);
		
		List<City> cities2 = new ArrayList<>();
		cities2.add(new City("changchun","长春市"));
		cities2.add(new City("jilin","吉林市"));
		cityMap.put("jilin", cities2);
		
		List<District> districts1 = new ArrayList<>();
		districts1.add(new District("hongshan", "洪山区"));
		districts1.add(new District("jiangxia", "江夏区"));
		districtMap.put("wuhan", districts1);
		
		List<District> districts2 = new ArrayList<>();
		districts2.add(new District("cengdu", "曾都区"));
		districts2.add(new District("guangshui", "广水区"));
		districtMap.put("suizhou", districts2);
		
		List<District> districts3 = new ArrayList<>();
		districts3.add(new District("chaoyang", "朝阳区"));
		districts3.add(new District("nanguan", "南关区"));
		districtMap.put("changchun", districts3);
		
		List<District> districts4 = new ArrayList<>();
		districts4.add(new District("chuanying", "船营区"));
		districts4.add(new District("longtan", "龙潭区"));
		districtMap.put("jilin", districts4);
	}
	
	@RequestMapping("/")
	public String selectPage(){
		return "select";
	}
	
	/**
	 * TODO 选择省份
	 */
	@RequestMapping(value="province",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selectProvince(){
		JSONObject jsonObject = new JSONObject();
		// 对象转为数组
		String[] code = new String[provinceList.size()];
		String[] name = new String[provinceList.size()];
		for (int i = 0; i < provinceList.size(); i++) {
			code[i] = provinceList.get(i).getCode();
			name[i] = provinceList.get(i).getName();
		}
		jsonObject.put("code", code);
		jsonObject.put("name", name);
		return jsonObject.toJSONString();
	}
	
	/**
	 * TODO 选择市
	 */
	@RequestMapping(value = "city",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selectCity(@RequestBody String provinceCode){
		String temp = provinceCode.substring(provinceCode.indexOf("=")+1,provinceCode.length());
		System.out.println(temp);
		JSONObject jsonObject = new JSONObject();
		List<City> list = cityMap.get(temp);
		for (City city : list) {
			System.out.println(city.toString());
		}
		// 对象转为数组
		String[] code = new String[list.size()];
		String[] name = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			code[i] = list.get(i).getCode();
			name[i] = list.get(i).getName();
		}
		jsonObject.put("code", code);
		jsonObject.put("name", name);
		return jsonObject.toJSONString();
	}

	/**
	 * TODO 选择区
	 */
	@ResponseBody
	@RequestMapping(value="district",produces="text/html;charset=UTF-8")
	public String selectDistrict(String cityCode){
		System.out.println(cityCode);
		JSONObject jsonObject = new JSONObject();
		List<District> list = districtMap.get(cityCode);
		for (District district : list) {
			System.out.println(district.toString());
		}
		// 对象转为数组
		String[] code = new String[list.size()];
		String[] name = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			code[i] = list.get(i).getCode();
			name[i] = list.get(i).getName();
		}
		jsonObject.put("code", code);
		jsonObject.put("name", name);
		return jsonObject.toJSONString();
	}

}
