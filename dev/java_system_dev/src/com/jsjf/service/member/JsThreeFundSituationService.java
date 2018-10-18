package com.jsjf.service.member;

import java.util.List;
import java.util.Map;

public interface JsThreeFundSituationService {

	public List<Map<String, Object>> getThreeFundSituation(Map<String, Object> map);
	
	public int getThreeFundSituationCount(Map<String, Object> map);
	
	public Map<String, Object> getThreeFundSituationSum(Map<String, Object> map);

	public void updateThreeFundSituation(Map<String, Object> map);



}
