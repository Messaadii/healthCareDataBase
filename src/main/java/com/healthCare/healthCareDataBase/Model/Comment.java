package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="commentId")
	private Long commentId;
	
	@Column(name="commentPostDate")
	private String commentPostDate;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="postedBy")
	private Long postedBy;
	
	@Column(name="postId")
	private Long postId;
	
	@Column(name="commentPoints")
	private int commentPoints;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getCommentPostDate() {
		return commentPostDate;
	}

	public void setCommentPostDate(String commentPostDate) {
		this.commentPostDate = commentPostDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(Long postedBy) {
		this.postedBy = postedBy;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public int getCommentPoints() {
		return commentPoints;
	}

	public void setCommentPoints(int commentPoints) {
		this.commentPoints = commentPoints;
	}

}
