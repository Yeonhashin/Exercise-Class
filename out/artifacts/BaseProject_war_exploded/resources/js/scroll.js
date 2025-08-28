let scrollTimeout;

window.addEventListener('scroll', () => {
    if (scrollTimeout) clearTimeout(scrollTimeout);

    scrollTimeout = setTimeout(() => {
        const scrollBottom = window.innerHeight + window.scrollY;
        const pageHeight = document.documentElement.scrollHeight;

        if (Math.abs(scrollBottom - pageHeight) < 1) {
            console.log('바닥에 닿았음! loadMore 실행');
            loadMore();
        }
    }, 100); // 100ms 지연 (스크롤 멈췄을 때)
});


let offset = 5;
const size = 5;
let isLoading = false;
let hasMore = true;

function loadMore() {
    if (isLoading || !hasMore) return;

    isLoading = true;

    const params = new URLSearchParams({
        offset: offset,
        size: size,
        searchClassDate: document.getElementById('searchClassDate')?.value || '',
        searchClassName: document.getElementById('className')?.value || '',
        searchInstructor: document.getElementById('instructor')?.value || ''
    });
    console.log('loadMore!!!!!');

    fetch(`/class/list/more?${params.toString()}`)
        .then(response => {
            if (!response.ok) throw new Error("Network response was not ok");
            return response.json();
        })
        .then(data => {
            const list = data.moreClasses;
            hasMore = data.hasMore;

            if (!list || Object.keys(list).length === 0) return;

            Object.entries(list).forEach(([date, classList]) => {
                const tableSelector = `table[data-date="${CSS.escape(date)}"]`;
                const container = document.getElementById('searchResultTables');

                let existingTable = document.querySelector(tableSelector);
                let tbody;

                if (!existingTable) {
                    const newTable = document.createElement('table');
                    newTable.className = 'search-table';
                    newTable.setAttribute('data-date', date);

                    newTable.innerHTML = `
                        <thead>
                            <tr>
                                <th class="entry-header" colspan="4">${date}</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    `;
                    container.appendChild(newTable);
                    existingTable = newTable;
                }

                tbody = existingTable.querySelector('tbody');

                classList.forEach(classmap => {
                    const isReserved = classmap.isReserved == 1;
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td>
                            ${classmap.class_date}<br>
                            ${classmap.class_start_time} - ${classmap.class_end_time}<br>
                            ${classmap.instructor_name}<br>
                            ${classmap.class_point_name}
                        </td>
                        <td class="lesson-title">${classmap.class_name}</td>
                        <td>${classmap.class_type_name}</td>
                        <td>
                            ${isReserved ? `
                            <span class="text-reserved">
                                <i class="bi bi-check-circle-fill me-1"></i> 예약중
                            </span>
                                <button type="button"
                                    class="btn-cancel open-cancel-modal"
                                    data-bs-toggle="modal"
                                    data-bs-target="#reserveModal"
                                    data-class-id="${classmap.id}"
                                    data-class-type-name="${classmap.class_type_name}"
                                    data-class-name="${classmap.class_name}"
                                    data-class-instructor-name="${classmap.instructor_name}"
                                    data-class-start-time="${classmap.class_start_time}"
                                    data-class-end-time="${classmap.class_end_time}"
                                    data-class-date="${classmap.class_date}"
                                    data-reservate-id="${classmap.reservation_id}">
                                    예약 취소
                                </button>
                            ` : `
                                <button type="button"
                                    class="btn-reserve mt-1 open-reserve-modal"
                                    data-bs-toggle="modal"
                                    data-bs-target="#reserveModal"
                                    data-class-id="${classmap.id}"
                                    data-class-type-name="${classmap.class_type_name}"
                                    data-class-name="${classmap.class_name}"
                                    data-class-instructor-name="${classmap.instructor_name}"
                                    data-class-start-time="${classmap.class_start_time}"
                                    data-class-end-time="${classmap.class_end_time}"
                                    data-class-date="${classmap.class_date}">
                                    예약하기
                                </button>
                            `}
                        </td>
                    `;
                    tbody.appendChild(row);
                });
            });

            offset += size;
        })
        .catch(error => {
            console.error("무한 스크롤 에러:", error);
        })
        .finally(() => {
            isLoading = false;
        });
}

// 📌 화면에 나타나는 걸 감지하는 감시자 생성
const observer = new IntersectionObserver((entries) => {
    const [entry] = entries;
    if (entry.isIntersecting && hasMore && !isLoading) {
        console.log('감시 요소 노출됨 → loadMore 실행');
        loadMore();
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const sentinel = document.getElementById('scrollSentinel');
    if (sentinel) {
        observer.observe(sentinel); // 요소 감시 시작
    }
});

// window.addEventListener('scroll', () => {
//     const scrollBottom = window.innerHeight + window.scrollY;
//     const threshold = document.body.offsetHeight - 1; // 하단 200px 전에 미리 로드
//
//     if (scrollBottom >= threshold) {
//         loadMore();
//     }
// });

document.addEventListener('DOMContentLoaded', () => {
    loadMore(); // 처음에 한 번 로딩
});
