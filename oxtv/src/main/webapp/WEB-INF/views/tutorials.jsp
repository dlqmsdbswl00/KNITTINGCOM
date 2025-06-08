<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>뜨개질 튜토리얼</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
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
	width: 220px;
	text-align: center;
}

.thumbnail {
	width: 200px;
	cursor: pointer;
	display: block;
	margin: 10px 0;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<div class="main-content">
		<h2>뜨개질 튜토리얼 🎥</h2>
		<p>사진을 클릭하면 해당 튜토리얼 영상으로 이동합니다</p>
		<div id="tutorial-container"></div>

		<script>
	console.log("🔥 tutorials.js loaded");

	// 유튜브 영상 ID 추출
	function extractVideoId(url) {
	    try {
	        const parsedUrl = new URL(url);
	        const hostname = parsedUrl.hostname;
	        
	        if (hostname === 'youtu.be') {
	            return parsedUrl.pathname.replace('/', '');
	        }

	        if (hostname.includes('youtube.com')) {
	            const vParam = parsedUrl.searchParams.get('v');
	            if (vParam) return vParam;

	            const pathMatch = parsedUrl.pathname.match(/\/(embed|v)\/([^/?]+)/);
	            if (pathMatch && pathMatch[2]) return pathMatch[2];
	        }

	        return null;
	    } catch (e) {
	        console.error('❌ Invalid URL:', url);
	        return null;
	    }
	}

	// 썸네일 + 제목 + 클릭 시 새창
	function createTutorialItem(item) {
	    const url = item["링크"];
	    console.log("링크:", url);

	    const videoId = extractVideoId(url);
	    console.log("videoId:", videoId);

	    if (!videoId) {
	        console.warn("❌ 잘못된 URL - videoId 없음:", item["링크"]);
	        return null;
	    }

	    const li = document.createElement('li');
	    li.className = 'tutorial-item';

	    const thumbnail = document.createElement('img');
	    thumbnail.src = item["썸네일"];
	    thumbnail.className = 'thumbnail';

	    thumbnail.onerror = function () {
	        this.src = '/img/default-thumbnail.jpg'; // 기본 썸네일 경로
	    };

	    const caption = document.createElement('div');
	    caption.textContent = item["제목"];

	    // 👉 클릭 시 유튜브 링크 새 창으로
	    thumbnail.addEventListener('click', () => {
	        window.open(url, '_blank');
	    });

	    li.appendChild(thumbnail);
	    li.appendChild(caption);

	    return li;
	}

	// 페이지 로드 시 실행
	window.addEventListener('DOMContentLoaded', function () {
	    fetch('/tutorials/data')
	    .then(res => res.json())
	    .then(data => {
	        console.log("🔥 받아온 튜토리얼 데이터:", data);
	        const container = document.getElementById('tutorial-container');
	        
	     	// 출력 순서 지정
	        const categoryOrder = ["코바늘", "대바늘"];
	     	
	        categoryOrder.forEach(category => {
	            const tutorials = data[category];
	            if (!tutorials) return;

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
	        });
	    })
	    .catch(err => {
	        console.error('에러 발생:', err);
	        document.getElementById('tutorial-container').textContent = '데이터를 불러오지 못했습니다.';
	    });
	});
	</script>
	</div>

</body>
</html>
