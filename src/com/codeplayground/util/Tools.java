package com.codeplayground.util;

public class Tools {

	public int checkPage(String requestPageNum, int totalCount, int slice) {
		int pageNum;

		try {
			pageNum = Integer.parseInt(requestPageNum);

			if (!(pageNum <= (totalCount - 1) / slice + 1 && pageNum > 0)) {
				pageNum = 1;
			}
		} catch (NumberFormatException e) {
			pageNum = 1;
		}

		return pageNum;
	}
}
