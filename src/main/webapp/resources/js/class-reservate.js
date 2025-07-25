document.addEventListener('DOMContentLoaded', function () {
    const modalClassId = document.getElementById('modalClassId');
    const modalReservationId = document.getElementById('modalReservationId');

    const confirmReserveBtn = document.getElementById('confirmReserveBtn');
    const confirmReserveWaitBtn = document.getElementById('confirmReserveWaitBtn');
    const confirmCancelBtn = document.getElementById('confirmCancelBtn');
    const confirmCancelWaitBtn = document.getElementById('confirmCancelWaitBtn');


    // 이벤트 위임: 전체 문서에 클릭 리스너 등록
    document.body.addEventListener('click', function (event) {
        const reserveBtn = event.target.closest('.open-reserve-modal');
        const reserveWaitBtn = event.target.closest('.open-reserve-wait-modal');
        const cancelBtn = event.target.closest('.open-cancel-modal');
        const cancelWaitBtn = event.target.closest('.open-cancel-wait-modal');

        if (reserveBtn) {
            openModal(reserveBtn, 'reserve');
        } else if (reserveWaitBtn) {
            openModal(reserveWaitBtn, 'reserve-wait');
        } else if (cancelBtn) {
            openModal(cancelBtn, 'cancel');
        } else if (cancelWaitBtn) {
            openModal(cancelWaitBtn, 'cancel-wait');
        }
    });

    function openModal(button, actionType) {
        const classTypeName = button.getAttribute('data-class-type-name');
        const classId = button.getAttribute('data-class-id');
        const reservationId = button.getAttribute('data-reservate-id');

        const className = button.getAttribute('data-class-name');
        const classInstructorName = button.getAttribute('data-class-instructor-name');
        const classDate = button.getAttribute('data-class-date');
        const classStartTime = button.getAttribute('data-class-start-time');
        const classEndTime = button.getAttribute('data-class-end-time');

        // 모달에 값 세팅
        modalClassId.value = classId;
        modalReservationId.value = reservationId;

        document.getElementById('modalClassTypeName').textContent = classTypeName;
        document.getElementById('modalClassName').textContent = className;
        document.getElementById('modalClassInstructor').textContent = classInstructorName;
        document.getElementById('modalClassDate').textContent = classDate;
        document.getElementById('modalClassStartTime').textContent = classStartTime;
        document.getElementById('modalClassEndTime').textContent = classEndTime;

        // 액션 타입에 따라 버튼 보여주기
        if (actionType === 'reserve') {
            confirmReserveBtn.style.display = 'inline-block';
            confirmReserveWaitBtn.style.display = 'none';
            confirmCancelBtn.style.display = 'none';
            confirmCancelWaitBtn.style.display = 'none';
        } else if (actionType === 'reserve-wait') {
            confirmReserveBtn.style.display = 'none';
            confirmReserveWaitBtn.style.display = 'inline-block';
            confirmCancelBtn.style.display = 'none';
            confirmCancelWaitBtn.style.display = 'none';
        } else if (actionType === 'cancel') {
            confirmReserveBtn.style.display = 'none';
            confirmReserveWaitBtn.style.display = 'none';
            confirmCancelBtn.style.display = 'inline-block';
            confirmCancelWaitBtn.style.display = 'none';
        } else if (actionType === 'cancel-wait') {
            confirmReserveBtn.style.display = 'none';
            confirmReserveWaitBtn.style.display = 'none';
            confirmCancelBtn.style.display = 'none';
            confirmCancelWaitBtn.style.display = 'inline-block';
        }
    }

    function handleReservation(isWait = false) {
        const classId = modalClassId.value;
        const url = `/reservation/add?classId=${classId}${isWait ? '&wait=true' : ''}`;

        fetch(url, {method: 'POST'})
            .then(response => response.json())
            .then(data => {
                const isSuccess = data.result === 'success';
                Swal.fire({
                    title: isSuccess ? (isWait ? '대기 예약 완료' : '예약 완료') : (isWait ? '대기 예약 실패' : '예약 실패'),
                    text: isSuccess
                        ? (isWait ? '대기 예약이 확정되었습니다!' : '예약이 확정되었습니다!')
                        : (isWait ? '대기 예약에 실패했습니다. 다시 시도해주세요.' : '예약에 실패했습니다. 다시 시도해주세요.'),
                    icon: isSuccess ? 'success' : 'error',
                    confirmButtonText: '확인'
                }).then(() => {
                    if (isSuccess) location.reload();
                });
            })
            .catch(error => {
                console.error('에러 발생:', error);
                Swal.fire({
                    title: '서버 오류',
                    text: '서버 오류가 발생했습니다.',
                    icon: 'error',
                    confirmButtonText: '확인'
                });
            });
    }

    function handleCancelReservation(isWait = false) {
        const reservationId = modalReservationId.value;
        const url = `/reservation/cancel?reservationId=${reservationId}${isWait ? '&wait=true' : ''}`;

        fetch(url, {method: 'POST'})
            .then(response => response.json())
            .then(data => {
                const isSuccess = data.result === 'success';
                Swal.fire({
                    title: isSuccess ? (isWait ? '대기 예약 취소 완료' : '예약 취소 완료') : (isWait ? '대기 예약 취소 실패' : '예약 취소 실패'),
                    text: isSuccess
                        ? (isWait ? '대기 예약 취소가 확정되었습니다!' : '예약이 취소되었습니다!')
                        : (isWait ? '대기 예약 취소에 실패했습니다. 다시 시도해주세요.' : '예약 취소에 실패했습니다. 다시 시도해주세요.'),
                    icon: isSuccess ? 'success' : 'error',
                    confirmButtonText: '확인'
                }).then(() => {
                    if (isSuccess) location.reload();
                });
            })
            .catch(error => {
                console.error('에러 발생:', error);
                Swal.fire({
                    title: '서버 오류',
                    text: '서버 오류가 발생했습니다.',
                    icon: 'error',
                    confirmButtonText: '확인'
                });
            });
    }

    // 예약 버튼 클릭
    confirmReserveBtn.addEventListener('click', () => handleReservation(false));

    // 대기예약 버튼 클릭
    confirmReserveWaitBtn.addEventListener('click', () => handleReservation(true));

    // 예약 취소 버튼 클릭
    confirmCancelBtn.addEventListener('click', () => handleCancelReservation(false));

    // 대기예약 취소 버튼 클릭
    confirmCancelWaitBtn.addEventListener('click', () => handleCancelReservation(true));

});

// let offset = 0;
// document.getElementById('loadMoreBtn')?.addEventListener('click', function () {
//     offset += 10; // 클릭할 때마다 10씩 증가
//     const params = new URLSearchParams({
//         offset: offset,
//         size: 10,
//         searchClassDate: document.getElementById('searchClassDate')?.value || '',
//         searchClassName: document.getElementById('className')?.value || '',
//         searchInstructor: document.getElementById('instructor')?.value || ''
//     });
//
//     fetch(`/class/list/more?${params.toString()}`)
//         .then(response => {
//             if (!response.ok) throw new Error("Network response was not ok");
//             return response.json();
//         })
//         .then(data => {
//             const list = data.moreClasses;
//             const hasMore = data.hasMore;
//
//             if (!list || Object.keys(list).length === 0) return;
//
//             Object.entries(list).forEach(([date, classList]) => {
//                 const tableSelector = `table[data-date="${CSS.escape(date)}"]`;
//                 const container = document.getElementById('searchResultTables');
//
//                 let existingTable = document.querySelector(tableSelector);
//                 let tbody;
//
//                 if (!existingTable) {
//                     const newTable = document.createElement('table');
//                     newTable.className = 'search-table';
//                     newTable.setAttribute('data-date', date);
//
//                     newTable.innerHTML = `
//                         <thead>
//                             <tr>
//                                 <th class="entry-header" colspan="4">${date}</th>
//                             </tr>
//                         </thead>
//                         <tbody></tbody>
//                     `;
//
//                     container.appendChild(newTable);
//                     existingTable = newTable;
//                 }
//
//                 tbody = existingTable.querySelector('tbody');
//
//                 classList.forEach(classmap => {
//                     const isReserved = classmap.isReserved == 1;
//                     const row = document.createElement('tr');
//                     row.innerHTML = `
//                         <td>
//                             ${classmap.class_date}<br>
//                             ${classmap.class_start_time} - ${classmap.class_end_time}<br>
//                             ${classmap.instructor_name}<br>
//                             ${classmap.class_point_name}
//                         </td>
//                         <td class="lesson-title">${classmap.class_name}</td>
//                         <td>${classmap.class_type_name}</td>
//                         <td>
//                             ${isReserved ? `
//                             <span class="text-reserved">
//                                 <i class="bi bi-check-circle-fill me-1"></i> 예약중
//                             </span>
//                                 <button type="button"
//                                     class="btn-cancel open-cancel-modal"
//                                     data-bs-toggle="modal"
//                                     data-bs-target="#reserveModal"
//                                     data-class-id="${classmap.id}"
//                                     data-class-type-name="${classmap.class_type_name}"
//                                     data-class-name="${classmap.class_name}"
//                                     data-class-instructor-name="${classmap.instructor_name}"
//                                     data-class-start-time="${classmap.class_start_time}"
//                                     data-class-end-time="${classmap.class_end_time}"
//                                     data-class-date="${classmap.class_date}"
//                                     data-reservate-id="${classmap.reservation_id}">
//                                     예약 취소
//                                 </button>
//                             ` : `
//                                 <button type="button"
//                                     class="btn-reserve mt-1 open-reserve-modal"
//                                     data-bs-toggle="modal"
//                                     data-bs-target="#reserveModal"
//                                     data-class-id="${classmap.id}"
//                                     data-class-type-name="${classmap.class_type_name}"
//                                     data-class-name="${classmap.class_name}"
//                                     data-class-instructor-name="${classmap.instructor_name}"
//                                     data-class-start-time="${classmap.class_start_time}"
//                                     data-class-end-time="${classmap.class_end_time}"
//                                     data-class-date="${classmap.class_date}">
//                                     예약하기
//                                 </button>
//                             `}
//                         </td>
//                     `;
//                     tbody.appendChild(row);
//                 });
//             });
//
//             if (!hasMore) {
//                 document.getElementById('loadMoreBtn').style.display = 'none';
//             }
//         })
//         .catch(error => {
//             console.error("더보기 요청 중 에러 발생:", error);
//             alert("수업 더보기 중 오류가 발생했습니다. 콘솔을 확인해주세요.");
//         });
// });

let offset = 0;
const size = 3;
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

window.addEventListener('scroll', () => {
    const scrollBottom = window.innerHeight + window.scrollY;
    const threshold = document.body.offsetHeight - 1; // 하단 200px 전에 미리 로드

    if (scrollBottom >= threshold) {
        loadMore();
    }
});

document.addEventListener('DOMContentLoaded', () => {
    loadMore(); // 처음에 한 번 로딩
});


document.getElementById('resetSearchBtn')?.addEventListener('click', function () {
    document.getElementById('searchClassDate').value = '';
    document.getElementById('className').value = '';
    document.getElementById('instructor').value = '';
    document.getElementById('searchForm').submit();
});
