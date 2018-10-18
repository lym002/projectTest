package com.jsjf.dao.seq;

import com.jsjf.model.system.SeqNo;

public interface SeqNoDAO {
	public SeqNo getById(String category);
	
	public void update(SeqNo seqNo);
	
	public void add(SeqNo seqNo);
}
