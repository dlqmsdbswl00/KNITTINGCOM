<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>뜨개질 튜토리얼</title>

<style>
.tutorial-list {
	list-style-type: none;
	padding: 0;
	margin: 0 0 30px 0;
	display: flex;
	flex-wrap: wrap;
	gap: 15px;
}

.tutorial-item {
	width: 220px; /* 썸네일 + 제목 넣을 공간 */
	text-align: center;
}

.thumbnail {
	width: 200px;
	cursor: pointer;
	display: block;
	margin: 10px 0;
}

.modal {
	display: none;
	position: fixed;
	z-index: 9999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.7);
}

.modal-content {
	position: relative;
	background: #fff;
	width: 600px;
	margin: 10% auto;
	padding: 20px;
	text-align: center;
}

.close {
	position: absolute;
	top: 5px;
	right: 10px;
	font-size: 25px;
	cursor: pointer;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<h2>뜨개질 튜토리얼 🎥</h2>

	<div id="tutorial-container"></div>

	<!-- 모달 영역 추가 -->
	<div id="video-modal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<iframe id="youtube-frame" width="560" height="315" frameborder="0" allowfullscreen></iframe>
		</div>
	</div>

	<script>
		console.log("🔥 tutorials.js loaded");

	    // 유튜브 영상 ID 추출
	   function extractVideoId(url) {
	    try {
	        const parsedUrl = new URL(url);
	        if (parsedUrl.hostname === 'youtu.be') {
	            // 도메인 바로 다음 경로가 영상ID
	            return parsedUrl.pathname.slice(1); // '/' 떼고 반환
	        }
	        // 일반 유튜브 URL은 v 파라미터로 뽑기
	        return parsedUrl.searchParams.get('v');
	    } catch (e) {
	        return null; // URL 형식 이상하면 null
	    }
	}

    // 썸네일 + 제목 + 모달 or 링크
    function createTutorialItem(item) {
       
     	const videoId = extractVideoId(item["링크"]);
        console.log("원본 링크:", item["링크"]);
        console.log("추출된 videoId:", videoId);
        
        if (!videoId) {
            console.warn("❌ 잘못된 URL - videoId 없음:", item["링크"]);
            return null;
        }
        
        const li = document.createElement('li');
        li.className = 'tutorial-item';
        

        const thumbnail = document.createElement('img');
        thumbnail.src = `https://img.youtube.com/vi/${videoId}/hqdefault.jpg`;
        thumbnail.className = 'thumbnail';

        thumbnail.onerror = function () {
            this.src = '/img/default-thumbnail.jpg'; // 직접 준비한 기본 이미지 경로
        };

        const caption = document.createElement('div');
        caption.textContent = item["제목"];

        li.appendChild(thumbnail);
        li.appendChild(caption);
        
        // 모달 재생 or 링크 새창 열기
	    thumbnail.addEventListener('click', () => {
	        const iframe = document.getElementById('youtube-frame');
	        iframe.src = `https://www.youtube.com/embed/${videoId}?autoplay=1`;
	        document.getElementById('video-modal').style.display = 'block';
	    });
        return li;
    }
	    // 페이지 로드 시 실행
	    // 모달 닫기
	    window.addEventListener('DOMContentLoaded', function () {
	    // 데이터 로드
	    fetch('/tutorials/data')
	        .then(response => response.json())
	        .then(data => {
	            const container = document.getElementById('tutorial-container');
	            for (const category in data) {
	                const categoryTitle = document.createElement('div');
	                categoryTitle.className = 'category';
	                categoryTitle.textContent = category;
	
	                const list = document.createElement('ul');
	                list.className = 'tutorial-list';
	
	                data[category].forEach(item => {
	                    const li = createTutorialItem(item);
	                    if (li) list.appendChild(li);
	                });
	
	                container.appendChild(categoryTitle);
	                container.appendChild(list);
	            }
	        })
	        .catch(err => {
	            console.error('에러 발생:', err);
	            document.getElementById('tutorial-container').textContent = '데이터를 불러오지 못했습니다.';
	        });
	
	    // 모달 닫기 로직
	    const closeBtn = document.querySelector('.close');
	    const modal = document.getElementById('video-modal');
	    const iframe = document.getElementById('youtube-frame');
	
	    closeBtn.addEventListener('click', () => {
	        modal.style.display = 'none';
	        iframe.src = ''; // 영상 중지용
	    });
	
	    // ESC로 모달 닫기
	    window.addEventListener('keydown', (e) => {
	        if (e.key === 'Escape') {
	            modal.style.display = 'none';
	            iframe.src = '';
        }
    });
});
</script>


</body>
</html>

