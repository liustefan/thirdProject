package com.zkhk.entity;


public class Pagination {
  public static final int DEF_COUNT = 10;
  private int totalCount;
  public int pageSize = 10;
  public int pageNow = 1;

  public static int cpn(Integer pageNow)
  {
     return (pageNow == null) || (pageNow.intValue() < 1) ? 1 : pageNow.intValue();
  }

  public static int cpn(int pageNow)
  {
     return pageNow < 1 ? 1 : pageNow;
  }

  public void adjustPageNow()
  {
     if (this.pageNow == 1)
       return;
     int tp = getTotalPage();
     if (this.pageNow > tp)
       this.pageNow = tp;
  }

  public int getTotalCount()
  {
     return this.totalCount;
  }

  public int getTotalPage()
  {
     int totalPage = this.totalCount / this.pageSize;
     if ((totalPage == 0) || (this.totalCount % this.pageSize != 0))
       totalPage++;
     return totalPage;
  }

  public int getPageSize()
  {
     return this.pageSize;
  }

  public int getPageNow()
  {
     return this.pageNow;
  }

  public boolean isFirstPage()
  {
     return this.pageNow <= 1;
  }

  public boolean isLastPage()
  {
     return this.pageNow >= getTotalPage();
  }

  public int getNextPage()
  {
     if (isLastPage()) {
       return this.pageNow;
    }
     return this.pageNow + 1;
  }

  public int getPrePage()
  {
     if (isFirstPage()) {
       return this.pageNow;
    }
     return this.pageNow - 1;
  }

  public int getFirstResult()
  {
     return (this.pageNow - 1) * this.pageSize;
  }

  public void setTotalCount(int totalCount)
  {
     if (totalCount < 0)
       this.totalCount = 0;
    else
       this.totalCount = totalCount;
  }

  public void setPageSize(int pageSize)
  {
     if (pageSize < 1)
       this.pageSize = 20;
    else
       this.pageSize = pageSize;
  }

  public void setPageNow(int pageNow)
  {
     if (pageNow < 1)
       this.pageNow = 1;
    else
       this.pageNow = pageNow;
  }

}
