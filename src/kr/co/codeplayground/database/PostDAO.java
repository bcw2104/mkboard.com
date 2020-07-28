package kr.co.codeplayground.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.codeplayground.util.DBUtil;

public class PostDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int pageUnit;

	public PostDAO() {
		conn = DBUtil.getConnection();
		pstmt = null;
		rs = null;

		pageUnit = 18;
	}

	public int getTotalPostCount(BoardDTO boardDTO) {
		return getTotalPostCount(boardDTO, "", "");
	}

	public int getTotalPostCount(BoardDTO boardDTO, String postTitle, String author) {
		int count = 0;
		String condition = "=";
		String boardId = boardDTO.getBoardId();
		if (boardId.equals("")) {
			condition = "LIKE";
			boardId = "%";
		}
		String sql = "SELECT count(*) as count FROM tbl_post NATURAL JOIN tbl_board " + "WHERE board_id "
				+ condition + " ? AND category_id = ? AND post_title LIKE ? AND author LIKE ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardId);
			pstmt.setString(2, boardDTO.getCategoryId());
			pstmt.setString(3, "%" + postTitle + "%");
			pstmt.setString(4, "%" + author + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt("count");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return count;
	}

	public ArrayList<PostDTO> getPostList(String field, BoardDTO boardDTO , int pageNum) {
		return getPostList(field, boardDTO, "", "", pageNum);
	}

	public ArrayList<PostDTO> getPostList(String field, BoardDTO boardDTO, String postTitle,String author, int pageNum) {
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();

		String condition = "=";
		String boardId = boardDTO.getBoardId();
		if (boardId.equals("")) {
			condition = "LIKE";
			boardId = "%";
		}
		String sql = "SELECT * FROM "
				+ "(SELECT rownum as row_num ,tbl_post.* FROM (SELECT * FROM tbl_post NATURAL JOIN "
				+ "(SELECT * FROM tbl_board WHERE  board_id " + condition + " ? AND category_id = ?) "
				+ "WHERE post_title LIKE ? AND author LIKE ? ORDER BY " + field + " DESC) tbl_post)"
				+ "WHERE row_num BETWEEN ? AND ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardId);
			pstmt.setString(2, boardDTO.getCategoryId());
			pstmt.setString(3, "%" + postTitle + "%");
			pstmt.setString(4, "%" + author + "%");
			pstmt.setInt(5, pageUnit * (pageNum - 1) + 1);
			pstmt.setInt(6, pageUnit * pageNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostDTO postDTO = new PostDTO();
				postDTO.setPostId(rs.getInt("post_id"));
				postDTO.setPostTitle(rs.getString("post_title"));
				postDTO.setPostContent(rs.getString("post_content"));
				postDTO.setBoardId(boardId);
				postDTO.setAuthor(rs.getString("author"));
				postDTO.setCreateDate(rs.getTimestamp("create_date"));
				postDTO.setHits(rs.getInt("hits"));

				list.add(postDTO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public boolean postPost(PostDTO postDTO) {
		String sql = "INSERT INTO tbl_post VALUES((select count(*)+1 from tbl_post),?,?,?,?,sysdate,0)";
		boolean state = true;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, postDTO.getPostTitle());
			pstmt.setString(2, postDTO.getPostContent());
			pstmt.setString(3, postDTO.getBoardId());
			pstmt.setString(4, postDTO.getAuthor());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("게시글 업로드 중 오류가 발생했습니다.");
			e.printStackTrace();
			state = false;
		} finally {
			try {
				if (!pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return state;
	}
}
