package com.jsjf.model.activity;

/**
 * 抽奖奖品概率
 *
 */
public class DrLotteryParam {
	
	private Integer index;
	
	private Integer id;
	
	private Integer aid;//活动ID
	
	private Integer giftId;//奖品ID
	
	private String giftName;//奖品名称
	
	private double	probability; //奖品中奖概率

	public DrLotteryParam(){}
	
	public DrLotteryParam(Integer index, Integer id, Integer aid,
			Integer giftId, String giftName, double probability) {
		super();
		this.index = index;
		this.id = id;
		this.aid = aid;
		this.giftId = giftId;
		this.giftName = giftName;
		this.probability = probability;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
	
	
}
