package com.jsjf.service.seq;

import java.util.List;

public interface SeqService {

	public int getSeqNo(String key);

	public List<Integer> getSeqNoList(String key, int total,int digit);
	
	public List<String> generateLuckCodes(Integer pid, int total, String prefix,Integer digit);
	
}
