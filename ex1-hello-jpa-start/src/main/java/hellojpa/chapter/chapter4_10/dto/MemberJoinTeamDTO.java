package hellojpa.chapter.chapter4_10.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinTeamDTO {
	
	private Long   memberId;
	private String memberName;
	private Long   memberTeamId;
	private Long   teamId;
	private String teamName;
	
	public MemberJoinTeamDTO(Long memberId, String memberName, Long memberTeamId, Long teamId, String teamName) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberTeamId = memberTeamId;
		this.teamId = teamId;
		this.teamName = teamName;
	}
}
