package com.oxtv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TutorialController {
	// 1. 튜토리얼 페이지 보여주기
	@GetMapping("/tutorials")
	public String showTutorialPage() {
		return "tutorials"; // tutorials.jsp 뷰로 이동
	}

	// 2. JSON 데이터 제공 API
	@GetMapping("/tutorials/data")
	@ResponseBody
	public Map<String, List<Map<String, String>>> getTutorials() {
		Map<String, List<Map<String, String>>> tutorials = new HashMap<>();
		List<Map<String, String>> crochetTutorials = new ArrayList<>();
		crochetTutorials.add(Map.of("제목", "코잡기(사슬코)", "링크", "https://youtu.be/sy1_QzXzohE?si=7O4aH8NMrLpSEQvx", "썸네일",
				"https://img.youtube.com/vi/sy1_QzXzohE/hqdefault.jpg"));
		crochetTutorials.add(Map.of("제목", "짧은뜨기", "링크", "https://youtu.be/1WQWUz9CK7Q?si=ZgJm4a6lxmsTAFr8", "썸네일",
				"https://img.youtube.com/vi/1WQWUz9CK7Q/hqdefault.jpg"));
		crochetTutorials.add(Map.of("제목", "긴뜨기", "링크", "https://youtu.be/-aEvy7sB_zs?si=Zcj9DkTl8zClWUfm", "썸네일",
				"https://img.youtube.com/vi/-aEvy7sB_zs/hqdefault.jpg"));
		crochetTutorials.add(Map.of("제목", "한길긴뜨기", "링크", "https://youtu.be/6kFxSdtubpY?si=X1xc2LUyWmI3p_lz", "썸네일",
				"https://img.youtube.com/vi/6kFxSdtubpY/hqdefault.jpg"));
		crochetTutorials.add(Map.of("제목", "빼뜨기", "링크", "https://youtu.be/XeeRYCK1f4Q?si=JAgtTGl3_hCUIRRU", "썸네일",
				"https://img.youtube.com/vi/XeeRYCK1f4Q/hqdefault.jpg"));

		tutorials.put("코바늘", crochetTutorials);

		List<Map<String, String>> knittingTutorials = new ArrayList<>();
		knittingTutorials.add(Map.of("제목", "코잡기", "링크", "https://youtu.be/UuLurc5D0BM?si=yTLYYXIBqUrkoLJi", "썸네일",
				"https://img.youtube.com/vi/UuLurc5D0BM/hqdefault.jpg"));
		knittingTutorials.add(Map.of("제목", "겉뜨기", "링크", "https://youtu.be/pPeCqFDOois?si=c_yKZLv8xqKO452_", "썸네일",
				"https://img.youtube.com/vi/pPeCqFDOois/hqdefault.jpg"));
		knittingTutorials.add(Map.of("제목", "안뜨기", "링크", "https://youtu.be/hdqI6goBtBA?si=F-wF0lee_69XG_87", "썸네일",
				"https://img.youtube.com/vi/hdqI6goBtBA/hqdefault.jpg"));
		knittingTutorials.add(Map.of("제목", "메리야스뜨기", "링크", "https://youtu.be/ccpOgePvpXA?si=rNy7jh_HHjtRCxo8", "썸네일",
				"https://img.youtube.com/vi/ccpOgePvpXA/hqdefault.jpg"));
		knittingTutorials.add(Map.of("제목", "한코고무뜨기", "링크", "https://youtu.be/uIM6EGMh4-g?si=8PJ8h7VBbDPEcqqK", "썸네일",
				"https://img.youtube.com/vi/uIM6EGMh4-g/hqdefault.jpg"));

		tutorials.put("대바늘", knittingTutorials);

		return tutorials; // 자동으로 json 변환

	}

}
