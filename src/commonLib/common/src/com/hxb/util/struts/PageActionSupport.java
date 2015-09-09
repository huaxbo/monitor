package com.hxb.util.struts;


/**
 * ����action
 * @author Administrator
 *
 */
public class PageActionSupport  extends ConditionActionSupport {

	private static final long serialVersionUID = 19000101123412L;
	
	public static String queryStartKey = "_qStart" ;
	public static String queryCountKey = "_qCount" ;
	
	/** ÿҳ��ʾ������ */
	@PropertieType
	public int pageSize = 10;
	/** ��¼���� */
	@PropertieType
	public long itemTotal;
	/** ��ǰҳ */
	@PropertieType
	public int pageCurr;
	/** ��ҳ�� */
	@PropertieType
	public int pageTotal;
	// true �����ǵ�һ���� ��false����ǵ�һ�η���
	@PropertieType(_FORMPROPERTY_ANNO)
	public boolean firstTime = true ;
	
	/**
	 * ҳ��
	 */
	@PropertieType
	private int page;
	/**
	 * ÿҳ�������
	 */
	@PropertieType
	private int rows;
	
		
	public void firstRequest(){
		this.setNotFirstTime();
	}
	
	/**
	 * ��ҳ��������action����÷�ҳ
	 * @param total
	 */
	protected void pages(Long total){
		this.updateItemTotal(total);//�ܼ�¼��
		this.putParam(queryStartKey, this.getQueryStart());
		this.putParam(queryCountKey, this.getQueryCount());
	}
	
	
	private void updateItemTotal(Long itemTotal) {
		if(itemTotal == null){
			itemTotal = 0L ;
		}
		int pageSize = getPageSize();

		int pageCurr = getPageCurr();
		int pageTotal = 0 ;
		if (pageSize<1) {
			pageSize = 1;
		}
		pageTotal = (int)Math.ceil((double)itemTotal/pageSize);
		if (pageTotal==0) {
			pageTotal = 1;
		}
		if (pageCurr < 1) {
			pageCurr = 1;
		}
		if (pageCurr > pageTotal) {
			pageCurr = pageTotal;
		}
		//
		setItemTotal(itemTotal);
		setPageTotal(pageTotal);
		setPageSize(pageSize);
		setPageCurr(pageCurr);
	}

	private int getQueryStart() {
		long l = (getPageCurr() - 1) ;
		
		l = l * getPageSize() ;
		
		if(l < 0){
			l = 0 ;
		}
		return new Long(l).intValue();
	}
	
	private int getQueryCount() {
		int size = getPageSize() ;
				
		return new Long(getPageCurr() == getPageTotal() ? 
				(getItemTotal() - (getPageCurr() - 1) * size) : size).intValue();
	}

	public boolean isFirstTime() {
		return firstTime;
	}
	private void setNotFirstTime() {
		this.firstTime = false;
	}
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	public boolean getFirstTime() {
		return this.firstTime ;
	}
	public int getPageSize() {
		pageSize = rows;
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(long itemTotal) {
		this.itemTotal = itemTotal;
	}

	public int getPageCurr() {
		pageCurr = page;
		return pageCurr;
	}

	public void setPageCurr(int pageCurr) {
		this.pageCurr = pageCurr;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
