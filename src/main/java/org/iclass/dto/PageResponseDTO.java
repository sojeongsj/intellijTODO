package org.iclass.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Getter
@ToString
public class PageResponseDTO<E> {
	
	private int page ;
	private int pageSize;      
	private long total;
	
	private int start;			//페이지 목록 시작
	private int end;			//         마지막
	private int last;			//페이지 목록 없이 앞,뒤 페이지 이동을 위해 변경함.
	
	private boolean prev;
	private boolean next;
	private List<E> dtoList;
	
	@Builder(builderMethodName = "withAll")
	public PageResponseDTO (PageRequestDTO pageRequestDTO , List<E> list , long total) {
		this.page = pageRequestDTO.getPage();
		this.pageSize = pageRequestDTO.getSize();
	
		this.total=total;
		this.dtoList = list;
		
		this.end = (int)(Math.ceil(this.page/10.0))*10;
		this.start = this.end-9;
		
		this.last = (int)(Math.ceil((total/(double)pageSize)));	//마지막페이지 번호
		this.end = end > last ? last: end;
		
		this.prev = this.start>1;
		this.next = total > this.end * this.pageSize;
	}
	
}
