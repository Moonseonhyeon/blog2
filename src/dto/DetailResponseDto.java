package dto;

import lombok.Builder;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Board;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailResponseDto {
	private BoardResponseDto boardDto;
	private List<ReplyResponseDto> replyDtos;
}
