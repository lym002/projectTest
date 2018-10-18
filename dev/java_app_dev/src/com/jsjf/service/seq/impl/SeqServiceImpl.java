package com.jsjf.service.seq.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsjf.dao.seq.SeqNoDAO;
import com.jsjf.model.system.SeqNo;
import com.jsjf.service.seq.SeqService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
public class SeqServiceImpl implements SeqService{
    @Autowired  
    private SeqNoDAO seqNoDao; 
	@Autowired
	private RedisClientTemplate redisClientTemplate; 
    @Override  
    public int getSeqNo(String category){  
        SeqNo seqNo = seqNoDao.getById(category);  
        //序列号从1001开始
        if(null == seqNo){
        	//不存在就新增
        	seqNo = new SeqNo();
        	seqNo.setCategory(category);
        	seqNo.setCurrNo(1001);
        	seqNo.setAddDate(new Date());
        	seqNoDao.add(seqNo);
        	return 1001;
        }else{
        	int next = seqNo.getCurrNo() + 1;
        	seqNo.setCurrNo(next);
        	seqNoDao.update(seqNo);
        	return next;
        }
    } 
    @Override
    public List<Integer> getSeqNoList(String category, int total,int digit){  
        SeqNo seqNo = seqNoDao.getById(category);  
    	List<Integer>seqNos = new ArrayList<>();
        //序列号从1001开始
        if(null == seqNo){//不存在就新增
        	String currNo = "1";
        	for(int i = 1;i<digit;i++){
        		currNo+="0";
        	}
        	
        	seqNo = new SeqNo();
        	seqNo.setCategory(category);
        	seqNo.setAddDate(new Date());
        	int maxCurrNo = generateSeqNos(Integer.parseInt(currNo), total, seqNos);
        	seqNo.setCurrNo(maxCurrNo);
        	seqNoDao.add(seqNo);
        }else{
        	int maxCurrNo = generateSeqNos(seqNo.getCurrNo(), total, seqNos);
        	seqNo.setCurrNo(maxCurrNo);
        	seqNoDao.update(seqNo);
        }
    	return seqNos;
    } 
    //一次生成多个序列号
    private int generateSeqNos(int currNo, int total, List<Integer>seqNos){
    	for(int i = 1; i <= total; i ++){
    		currNo++;
    		seqNos.add(currNo);
    	}
    	return currNo;
    }
    /**
     * 获取幸运码
     * @param pid 产品ID
     * @param total 幸运码个数
     * @param prefix 前缀
     */
    @Override
    public List<String> generateLuckCodes(Integer pid, int total, String prefix,Integer digit){
    	boolean lockFlag = redisClientTemplate.tryLock("product.luckCode."+pid, 3, TimeUnit.SECONDS,true);
    	List<String>result = new ArrayList<>();
    	if(lockFlag){
    		List<Integer>seqNos = getSeqNoList(pid.toString(), total,digit);
    		if(!seqNos.isEmpty()){
    			for(int i = 0; i < seqNos.size(); i ++){
    				result.add(prefix + seqNos.get(i)); 
    			}
    		}
    	}
    	return result;
    }	
}
