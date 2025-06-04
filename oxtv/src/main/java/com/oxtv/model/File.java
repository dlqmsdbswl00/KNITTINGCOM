package com.oxtv.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "file")
public class File {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	private String originalName;
	private String savedName;
	private String uploadPath;

	@Column(name = "upload_date")
	private LocalDateTime uploadDate;

	@Column(name = "saved_path")
	private String savedPath;

	public String getSavedPath() {
	    return "C:/Users/dlqms/hk-source/miniproject/oxtv/src/main/resources/static" + this.uploadPath;
	}

}
