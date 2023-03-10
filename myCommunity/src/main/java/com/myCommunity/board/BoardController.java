package com.myCommunity.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myCommunity.comment.CommentServiceImpl;
import com.myCommunity.comment.CommentVo;
import com.myCommunity.criteria.Criteria;
import com.myCommunity.criteria.CriteriaMapper;
import com.myCommunity.criteria.CriteriaService;
import com.myCommunity.criteria.Criteria;
import com.myCommunity.criteria.Pagination;
import com.myCommunity.login.LoginVo;
import com.myCommunity.user.UserVo;

//test
@Controller
@RequestMapping("/boards")
public class BoardController {
	@Autowired
	private BoardServiceImpl boardService;
	@Autowired
	CommentServiceImpl commentService;
	@Autowired
	CriteriaService criteriaService;

	
	@RequestMapping
	public String index(HttpServletRequest request, Model model) {
		List<BoardVo> boardList = boardService.findAll();
		List<BoardVo> travelList = boardService.findAllTravel();
		List<BoardVo> hobbyList = boardService.findAllHobby();
		List<BoardVo> computerList = boardService.findAllComputer();
		List<BoardVo> stockList = boardService.findAllStock();
		List<BoardVo> freeList = boardService.findAllFree();
		
		HttpSession session = request.getSession(false);
		
		if (session == null) {

			model.addAttribute("boardList", boardList);
			model.addAttribute("travelList", travelList);
			model.addAttribute("hobbyList", hobbyList);
			model.addAttribute("computerList", computerList);
			model.addAttribute("stockList", stockList);
			model.addAttribute("freeList", freeList);
			
			return "board/index";
		}
		
		LoginVo loginUser = (LoginVo)session.getAttribute("user");

		if (loginUser == null) {
			
			model.addAttribute("boardList", boardList);
			model.addAttribute("travelList", travelList);
			model.addAttribute("hobbyList", hobbyList);
			model.addAttribute("computerList", computerList);
			model.addAttribute("stockList", stockList);
			model.addAttribute("freeList", freeList);
			
			return "board/index";
		}

		model.addAttribute("boardList", boardList);
		model.addAttribute("travelList", travelList);
		model.addAttribute("hobbyList", hobbyList);
		model.addAttribute("computerList", computerList);
		model.addAttribute("stockList", stockList);
		model.addAttribute("freeList", freeList);
		
		model.addAttribute("user", loginUser);
	
		return "board/index";
	}
	
	@GetMapping("/{division}")
	public String divisionBoard(@PathVariable("division") String division, @RequestParam(value="page", required=false, defaultValue = "1") int page, Model model) {
		if(division.equals("hot")) {
			List<BoardVo> hotCnt = boardService.findAllHot();
			int totalCount = hotCnt.size();

			Pagination pn = new Pagination();
			Criteria pg = new Criteria();
			
			if(page <= 0) {
				page = 1;
			}
			
			pg.setPage(page);
			
			pn.setCriteria(pg);
			
			pn.setTotalCount(totalCount);

			if(pn.getEndPage() < page) {
				page = pn.getTotalPageCount();
				pg.setPage(page);
				
				pn.setCriteria(pg);
				
				pn.setTotalCount(totalCount);
			}
			
			int start = pn.getCriteria().getPageStart();
			int size = pn.getCriteria().getRecordSize();
			
			List<BoardVo> boardList = criteriaService.findAllHit(start, size);
			model.addAttribute("boardList", boardList);
			model.addAttribute("pagination", pn);
			model.addAttribute("tit", "?????? ???");
			model.addAttribute("enDivision", "hot");
			
			return "board/posts";
		}
	
		String endivision = boardService.dvchEK(division);
		
		int totalCount = criteriaService.count(endivision);

		Pagination pn = new Pagination();
		Criteria pg = new Criteria();
		
		if(page <= 0) {
			page = 1;
		}
		
		pg.setPage(page);
		
		pn.setCriteria(pg);
		
		pn.setTotalCount(totalCount);

		if(pn.getEndPage() < page) {
			page = pn.getTotalPageCount();
			pg.setPage(page);
			
			pn.setCriteria(pg);
			
			pn.setTotalCount(totalCount);
		}
		
		int start = pn.getCriteria().getPageStart();
		int size = pn.getCriteria().getRecordSize();
		
		List<BoardVo> boardList = criteriaService.findAll(start, size, endivision);

		model.addAttribute("boardList", boardList);
		model.addAttribute("pagination", pn);
		model.addAttribute("tit", endivision);
		model.addAttribute("enDivision", division);
			
		return "board/posts";
	}
	

	@GetMapping("/create")
	public String createPost(HttpServletRequest request, RedirectAttributes rttr) {
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			rttr.addFlashAttribute("errm", "????????? ??? ??? ????????? ???????????????.");
			return "redirect:/login";
		}
		if(session != null) {
			LoginVo loginVo = (LoginVo)session.getAttribute("user");
			if(loginVo == null) {
				rttr.addFlashAttribute("errm", "????????? ??? ??? ????????? ???????????????.");
				return "redirect:/login";
			}
		}
		
		return "board/createPost";
	}


	@PostMapping
	public String savePost(@ModelAttribute("board") BoardVo boardVo, HttpServletRequest request, RedirectAttributes rttr) {
		
		HttpSession session = request.getSession(false);
		
		LoginVo loginUser = new LoginVo();
		
		if(session == null) {
			rttr.addFlashAttribute("errm", "????????? ??? ??? ????????? ???????????????.");
			return "redirect:/login";
		}
		if(session != null) {
			loginUser = (LoginVo)session.getAttribute("user");
			
			if(loginUser == null) {
				rttr.addFlashAttribute("errm", "????????? ??? ??? ????????? ???????????????.");
				return "redirect:/login";
			}
		}
		
		if(boardVo.getTitle().isEmpty() || boardVo.getContents().isEmpty()) {
			rttr.addFlashAttribute("errm", "?????? ?????? ????????? ??????????????????.");
			return "redirect:/boards/create";
		}
		
		boardVo.setUserNickName(loginUser.getNickName());
		boardVo.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		boardService.save(boardVo);
		
		boardVo.setDivision(boardService.dvchKE(boardVo.getDivision()));
		
		rttr.addFlashAttribute("msgm", "?????? ?????????????????????.");
		
		return "redirect:/boards/"+boardVo.getDivision();
	}
	
	@GetMapping("/{division}/{id}")
	public String showPost(@PathVariable("id") int boardId, @RequestParam(value="page", required=false, defaultValue = "1") int page, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		
		BoardVo boardVo = boardService.findById(boardId);
		
		if(session == null) {
			boardService.hitsUp(boardId); 
		}
		if(session != null) {
			LoginVo loginUser = (LoginVo)session.getAttribute("user");
			if(loginUser == null) {
				boardService.hitsUp(boardId);
			}
			if(loginUser != null) {
				if(!loginUser.getNickName().equals(boardVo.getUserNickName())) {
					boardService.hitsUp(boardId);
				}
			}
		}
		
		List<CommentVo> commentList = commentService.commentList(boardId);
		List<CommentVo> commentSize = commentService.commentListdelete(boardId);
		
		model.addAttribute("tit", boardVo.getDivision());
		model.addAttribute("enDivision", boardService.dvchKE(boardVo.getDivision()));
		
		int totalCount = criteriaService.count(boardVo.getDivision());

		Pagination pn = new Pagination();
		Criteria pg = new Criteria();
		
		if(page <= 0) {
			page = 1;
		}
		
		pg.setPage(page);
		
		pn.setCriteria(pg);
		
		pn.setTotalCount(totalCount);

		if(pn.getEndPage() < page) {
			page = pn.getTotalPageCount();
			pg.setPage(page);
			
			pn.setCriteria(pg);
			
			pn.setTotalCount(totalCount);
		}
		
		int start = pn.getCriteria().getPageStart();
		int size = pn.getCriteria().getRecordSize();
		
		List<BoardVo> boardList = criteriaService.findAll(start, size, boardVo.getDivision());
		
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pagination", pn);
		model.addAttribute("board", boardVo);
		model.addAttribute("commentList", commentList);
		model.addAttribute("commentSize", commentSize);
		
		return "board/showPost";
		
		
	}
	
	@GetMapping("/{division}/{id}/edit")
	public String editPost(@PathVariable("division") String division, @PathVariable("id") int boardId, Model model,
			HttpServletRequest request, RedirectAttributes rttr) {
		BoardVo boardVo = boardService.findById(boardId);
		
		model.addAttribute("tit", boardVo.getDivision());
		String endivision = boardService.dvchKE(boardVo.getDivision());
		
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			rttr.addFlashAttribute("errm", "??????????????? ????????????.");

			return "redirect:/boards/"+ endivision + "/" + boardVo.getId();
		} 
		
		LoginVo loginUser = (LoginVo)session.getAttribute("user");
		
		if(loginUser == null) {
			rttr.addFlashAttribute("errm", "?????????????????? ????????????.");

			return "redirect:/boards/"+ endivision + "/" + boardVo.getId();
		}
		if(loginUser != null) {
			if(!loginUser.getNickName().equals(boardVo.getUserNickName())) {
				if(loginUser.getAuth() == 1) {
					model.addAttribute("division", division);
					model.addAttribute("board", boardVo);
						
					return "board/editPost";
				}
				rttr.addFlashAttribute("errm", "????????? ??? ?????? ????????? ???????????????.");

				return "redirect:/boards/"+ endivision + "/" + boardVo.getId();
			}
		}
		
		model.addAttribute("division", division);
		model.addAttribute("board", boardVo);
			
		return "board/editPost";
		
	}
	
	@PatchMapping("/{id}")
	public String updatePost(@ModelAttribute("board") BoardVo boardVo, @PathVariable("id") int boardId, RedirectAttributes rttr, Model model) {		
		boardVo.setDivision(boardService.dvchKE(boardVo.getDivision()));
		String select = boardService.dvchKE(boardVo.getDivision());
		
		if(boardVo.getTitle().isEmpty() || boardVo.getContents().isEmpty()) {
			rttr.addFlashAttribute("division", select);
			rttr.addFlashAttribute("errm", "??????????????? ????????? ??? ????????????.");
			return "redirect:/boards/" + select + "/" + boardVo.getId() + "/edit";
		}
		
		boardVo.setModifyTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				
		boardService.update(boardId, boardVo);	
		
		rttr.addFlashAttribute("msgm", "????????? ?????????????????????.");
		
		return "redirect:/boards/"+ select + "/" + boardVo.getId();
	}
	
	@DeleteMapping("/{id}")
	public String deletePost(@ModelAttribute("board") BoardVo boardVo, @PathVariable("id") int boardId, RedirectAttributes rttr) {
		boardVo.setDeleteTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		String division = boardService.dvchKE(boardVo.getDivision());
		
		boardService.delete(boardId, boardVo);
		rttr.addFlashAttribute("msgm", "????????? ??????????????????.");
		return "redirect:/boards/" + division;
	}
	
	@GetMapping("/srt")
	public String boardSort2(@RequestParam("srt") String sort, @RequestParam("tit") String tit, 
			@RequestParam(defaultValue = "?????????") String srt, @RequestParam(value="page", required=false, defaultValue = "1") int page, Model model) {
		String division = boardService.dvchKE(tit);
		
		if(sort.equals("?????????")) {
			int totalCount = criteriaService.count(tit);
			
			Pagination pn = new Pagination();
			Criteria pg = new Criteria();
			
			if(page <= 0) {
				page = 1;
			}
			
			pg.setPage(page);
			
			pn.setCriteria(pg);
			
			pn.setTotalCount(totalCount);

			if(pn.getEndPage() < page) {
				page = pn.getTotalPageCount();
				pg.setPage(page);
				
				pn.setCriteria(pg);
				
				pn.setTotalCount(totalCount);
			}
			
			int start = pn.getCriteria().getPageStart();
			int size = pn.getCriteria().getRecordSize();
			
			List<BoardVo> boardList = criteriaService.findHit(start, size, tit);
			
			model.addAttribute("boardList", boardList);
			model.addAttribute("pagination", pn);
			model.addAttribute("tit", tit);
			model.addAttribute("enDivision", division);
			model.addAttribute("srt", "?????????");
			
			return "board/posts";
		} 
		if(sort.equals("?????????")) {
			int totalCount = criteriaService.count(tit);
			
			Pagination pn = new Pagination();
			Criteria pg = new Criteria();
			
			if(page <= 0) {
				page = 1;
			}
			
			pg.setPage(page);
			
			pn.setCriteria(pg);
			
			pn.setTotalCount(totalCount);

			if(pn.getEndPage() < page) {
				page = pn.getTotalPageCount();
				pg.setPage(page);
				
				pn.setCriteria(pg);
				
				pn.setTotalCount(totalCount);
			}
			
			int start = pn.getCriteria().getPageStart();
			int size = pn.getCriteria().getRecordSize();
			
			List<BoardVo> boardList = criteriaService.findAll(start, size, tit);
			
			model.addAttribute("boardList", boardList);
			model.addAttribute("pagination", pn);
			model.addAttribute("tit", tit);
			model.addAttribute("enDivision", division);
			model.addAttribute("srt", "?????????");
			
			return "board/posts";
		}
		return "board/posts";
	}
		
}