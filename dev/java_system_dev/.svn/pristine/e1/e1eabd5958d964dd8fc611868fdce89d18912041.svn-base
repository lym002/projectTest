package com.jsjf.service.seq.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.Sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.dao.seq.SeqNoDAO;
import com.jsjf.model.system.SeqNo;
import com.jsjf.service.seq.SeqService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@Service
@Transactional
public class SeqServiceImpl implements SeqService{
    @Autowired  
    private SeqNoDAO seqNoDao; 
	@Autowired
	private RedisClientTemplate redisClientTemplate; 
	public int DEFAULT_INIT_VALUE = 1;
    public int DEFAULT_INCREMENT = 1;
    public int DEFAULT_MAX_VALUE = Integer.MAX_VALUE;
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
    @Override
	public int getSequence(String category) {
        return getSequence(category, RESET_NEVER, OVERFLOW_ERROR, DEFAULT_INIT_VALUE, DEFAULT_INCREMENT, DEFAULT_MAX_VALUE, null);
    }
    
    @Override
	public int getSequenceResetByDay(String category) {
        return getSequence(category, RESET_BY_DAY, OVERFLOW_ERROR, DEFAULT_INIT_VALUE, DEFAULT_INCREMENT, DEFAULT_MAX_VALUE, null);
    }
    @Override
    public synchronized int getSequence( String category, byte resetMode, byte overflowMode, int initValue, int increment, int maxValue, Calendar _current_) {
        if (_current_ == null) {
            _current_ = Calendar.getInstance();
        }
        Calendar currentCalendar = (Calendar) _current_.clone();
        currentCalendar.clear(Calendar.HOUR);
        currentCalendar.clear(Calendar.MINUTE);
        currentCalendar.clear(Calendar.SECOND);
        currentCalendar.clear(Calendar.MILLISECOND);

        int counter = initValue;
        SeqNo sequence = seqNoDao.getById(category); 
        if (null == sequence) {
            // 1st time to retrieve the sequence
            sequence = new SeqNo();
            sequence.setCategory(category);
            sequence.setCurrNo(initValue);
            sequence.setAddDate(new Date());
            sequence.setUpdateTime(new Date());
        	seqNoDao.add(sequence);
        } else {
            
            counter = sequence.getCurrNo();

            Date lastChangeDate = sequence.getUpdateTime();

            Calendar lastCalendar = Calendar.getInstance();
            lastCalendar.clear();
            lastCalendar.setTime(lastChangeDate);
            lastCalendar.clear(Calendar.HOUR);
            lastCalendar.clear(Calendar.MINUTE);
            lastCalendar.clear(Calendar.SECOND);
            lastCalendar.clear(Calendar.MILLISECOND);
//            log.debug("lastChangeCalendar:" + lastCalendar);

            switch (resetMode) {
                case RESET_BY_DAY:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
                            && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)
                            && lastCalendar.get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH)) {
                        counter = sequence.getCurrNo() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_BY_WEEK_OF_YEAR:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
                            && lastCalendar.get(Calendar.WEEK_OF_YEAR) == currentCalendar.get(Calendar.WEEK_OF_YEAR)) {
                        counter = sequence.getCurrNo() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_BY_WEEK_OF_MONTH:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
                            && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)
                            && lastCalendar.get(Calendar.WEEK_OF_MONTH) == currentCalendar.get(Calendar.WEEK_OF_MONTH)) {
                        counter = sequence.getCurrNo() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_BY_MONTH:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)
                            && lastCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)) {
                        counter = sequence.getCurrNo() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_BY_YEAR:
                    if (lastCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)) {
                        counter = sequence.getCurrNo() + increment;
                    } else {
                        counter = initValue;
                    }
                    break;
                case RESET_NEVER:
                    counter = sequence.getCurrNo() + increment;
                    break;
                default:
                    break;
            }

            if (counter > maxValue) {
                if (overflowMode == OVERFLOW_ERROR) {
                    throw new RuntimeException("Sequence with category [" + category +
                            "] returns value " + counter + " is greater than allowed maximum value " + maxValue);
                } else if (overflowMode == OVERFLOW_RESET) {
                    counter = initValue;
                }
            }
            sequence.setCurrNo(counter);
            sequence.setUpdateTime(currentCalendar.getTime());
        	seqNoDao.update(sequence);
        }
        return counter;
    }
}
