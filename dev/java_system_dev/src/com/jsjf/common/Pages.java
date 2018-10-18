package com.jsjf.common;

import java.util.List;

public class Pages<T> {

	private List<T> root = null; // 存储数据

	private int currePage; // 当前�?

	private int countPage; // 总页�?

	private int curreRow; // 当前显示的行�?

	private int countRow; // 总行�?

	public Pages() {
		this.currePage = 1;
		this.curreRow = 20;
	}

	/**
	 * 计算当前�?, query* 分页 参数�?
	 * 
	 * @return
	 */
	public int getPageAccount() {

		return (this.getCurrePage() - 1) * this.getCurreRow();
	}

	/**
	 * 获得当前�?
	 * 
	 * @return
	 */
	public int getCurrePage() {
		return currePage;
	}

	/**
	 * 设置当前
	 * 
	 * @param currePage
	 */
	public void setCurrePage(int currePage) {
		this.currePage = currePage;
	}

	/**
	 * 获得总页�?
	 * 
	 * @return
	 */
	public int getCountPage() {
		return countPage;
	}

	/**
	 * 保存总页�?
	 * 
	 * @param countPage
	 */
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	/**
	 * 获得总行�?
	 * 
	 * @return
	 */
	public int getCountRow() {
		return countRow;
	}

	/**
	 * 保存总行�?
	 * 
	 * @param countRow
	 */
	public void setCountRow(int countRow) {

		this.countRow = countRow;

		if (countRow % curreRow == 0) {
			this.countPage = countRow / curreRow;
		} else {
			this.countPage = (countRow / curreRow) + 1;
		}

	}

	/**
	 * 获得当前行数
	 * 
	 * @return
	 */
	public int getCurreRow() {
		return curreRow;
	}

	/**
	 * 保存当前行数
	 * 
	 * @param curreRow
	 */
	public void setCurreRow(int curreRow) {

		this.curreRow = curreRow;

	}

	public List<T> getRoot() {
		return root;
	}

	public void setRoot(List<T> root) {
		this.root = root;
	}

}
