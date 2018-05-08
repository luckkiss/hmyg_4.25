package com.hldj.hmyg.Ui.friend.bean.enums;


import com.hldj.hmyg.R;

/**
 * 经纪评分项
 * @author linzj
 * @Date 2017-1-9
 */
public enum AgentGrade{

/**
 * f (!quote) {
 drawable = getResources().getDrawable(R.mipmap.wd_gys_no);
 sptv_wd_gys.setText("申请成为供应商");
 */

	level0("level0","申请成为供应商", R.mipmap.wd_gys_no),
	/**
	 * 供应商
	 */
	level1("level1","合作供应商", R.mipmap.wd_gys_lv1),
	/**
	 * 合格供应商
	 */
	level2("level2","合格供应商", R.mipmap.wd_gys_lv2),
	/**
	 * 银牌供应商
	 */
	level3("level3","铜牌供应商", R.mipmap.wd_gys_lv3),
	/**
	 * 铜牌供应商
	 */
	level4("level4","银牌供应商", R.mipmap.wd_gys_lv4),
	/**
	 * 金牌供应商
	 */
	level5("level5","金牌供应商", R.mipmap.wd_gys_lv5);
	
	private String enumValue;
	private String enumText;
	/**
	 *  资源图片 id
	 */
	private int upScore;
	/**
	 * 下一个等级
	 */
	private String nextLevel;
	private AgentGrade(String enumValue, String enumText,int upScore) {
        this.enumValue = enumValue;
        this.enumText = enumText;
        this.upScore = upScore;
    }
    public String getEnumValue() {
        return enumValue;
    }
    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }
    public String getEnumText() {
        return enumText;
    }
    public void setEnumText(String enumText) {
        this.enumText = enumText;
    }
	/**
	 * 升级到下一个等级需要几分
	 * @return
	 */
	public int getUpScore() {
		return upScore;
	}
	/**
	 * 升级到下一个等级需要几分
	 * @param upScore
	 */
	public void setUpScore(int upScore) {
		this.upScore = upScore;
	}
	/**
	 * 获取下一个等级
	 * @return nextLevel
	 */
	public String getNextLevel() {
		return nextLevel;
	}
	/**
	 * 设置下一个等级
	 * @param nextLevel
	 */
	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}
    
	
}