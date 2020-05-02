package io.github.jaychoufans.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookType {

	private Integer bookTypeId;

	private String bookTypeName;

	private Integer bookTypeParentId;

	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bookTypeCreateTime;

	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date bookTypeLastModifyTime;

	private String bookTypeNote;

	// 不对应数据库字段
	private List<BookType> children = new ArrayList<>();

}