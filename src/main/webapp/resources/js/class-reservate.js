document.addEventListener('DOMContentLoaded', function () {
    const modalClassId = document.getElementById('modalClassId');
    const confirmReserveBtn = document.getElementById('confirmReserveBtn');
    const confirmCancelBtn = document.getElementById('confirmCancelBtn');

    // 이벤트 위임: 전체 문서에 클릭 리스너 등록
    document.body.addEventListener('click', function (event) {
        const reserveBtn = event.target.closest('.open-reserve-modal');
        const cancelBtn = event.target.closest('.open-cancel-modal');

        if (reserveBtn) {
            openModal(reserveBtn, 'reserve');
        } else if (cancelBtn) {
            openModal(cancelBtn, 'cancel');
        }
    });

    function openModal(button, actionType) {
        const classTypeName = button.getAttribute('data-class-type-name');
        const classId = button.getAttribute('data-class-id');

        const className = button.getAttribute('data-class-name');
        const classInstructorName = button.getAttribute('data-class-instructor-name');
        const classDate = button.getAttribute('data-class-date');
        const classStartTime = button.getAttribute('data-class-start-time');
        const classEndTime = button.getAttribute('data-class-end-time');

        // 모달에 값 세팅
        modalClassId.value = classId;
        document.getElementById('modalClassTypeName').textContent = classTypeName;
        document.getElementById('modalClassName').textContent = className;
        document.getElementById('modalClassInstructor').textContent = classInstructorName;
        document.getElementById('modalClassDate').textContent = classDate;
        document.getElementById('modalClassStartTime').textContent = classStartTime;
        document.getElementById('modalClassEndTime').textContent = classEndTime;

        // 액션 타입에 따라 버튼 보여주기
        if (actionType === 'reserve') {
            confirmReserveBtn.style.display = 'inline-block';
            confirmCancelBtn.style.display = 'none';
        } else if (actionType === 'cancel') {
            confirmReserveBtn.style.display = 'none';
            confirmCancelBtn.style.display = 'inline-block';
        }
    }

    // // 예약 확정 버튼 클릭 시 폼 제출
    // confirmReserveBtn.addEventListener('click', function () {
    //     document.getElementById('reserveForm').submit();
    // });

    // 예약 확정 버튼 클릭 시 AJAX 호출
    confirmReserveBtn.addEventListener('click', function () {
        const classId = modalClassId.value;
        fetch(`/reservation/add?classId=${classId}`, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                if (data.result === 'success') {
                    Swal.fire({
                        title: '예약 완료',
                        text: '예약이 확정되었습니다!',
                        icon: 'success',
                        confirmButtonText: '확인'
                    }).then(() => {
                        location.reload();
                    });
                } else {
                    Swal.fire({
                        title: '예약 실패',
                        text: '예약에 실패했습니다. 다시 시도해주세요.',
                        icon: 'error',
                        confirmButtonText: '확인'
                    });
                }
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
    });

    // 취소 확정 버튼 클릭 시 AJAX 호출
    confirmCancelBtn.addEventListener('click', function () {
        const classId = modalClassId.value;

        fetch(`/reservation/cancel?classId=${classId}`, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                if (data.result === 'success') {
                    Swal.fire({
                        title: '예약 취소 완료',
                        text: '예약이 취소되었습니다!',
                        icon: 'success',
                        confirmButtonText: '확인'
                    }).then(() => {
                        location.reload();
                    });
                } else {
                    Swal.fire({
                        title: '취소 실패',
                        text: '취소에 실패했습니다. 다시 시도해주세요.',
                        icon: 'error',
                        confirmButtonText: '확인'
                    });
                }
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
    });
});


let offset = 10;

document.getElementById('loadMoreBtn')?.addEventListener('click', function () {
    const params = new URLSearchParams({
        offset: offset,
        size: 10,
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
            const list = data.reservedClass;
            const reservedClassIds = data.reservedClassIds;
            const hasMore = data.hasMore;

            if (!list || Object.keys(list).length === 0) return;

            Object.entries(list).forEach(([date, classList]) => {
                const tableSelector = `table[data-date="${CSS.escape(date)}"]`;
                const container = document.getElementById('searchResultTables');

                let existingTable = document.querySelector(tableSelector);
                let tbody;

                if (!existingTable) {
                    // 새 날짜 테이블 생성
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
                    const isReserved = reservedClassIds.includes(classmap.id);
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
                                <span class="badge bg-success mb-1">예약중</span>
                                <button type="button"
                                    class="btn btn-danger btn-sm open-cancel-modal"
                                    data-bs-toggle="modal"
                                    data-bs-target="#reserveModal"
                                    data-class-id="${classmap.id}"
                                    data-class-type-name="${classmap.class_type_name}"
                                    data-class-name="${classmap.class_name}"
                                    data-class-instructor-name="${classmap.instructor_name}"
                                    data-class-start-time="${classmap.class_start_time}"
                                    data-class-end-time="${classmap.class_end_time}"
                                    data-class-date="${classmap.class_date}">
                                    예약 취소
                                </button>
                            ` : `
                                <button type="button"
                                    class="btn btn-primary btn-sm mt-1 open-reserve-modal"
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
                    tbody.appendChild(row); // ✅ 제대로 된 위치에 추가
                });
            });

            offset += 10;
            if (!hasMore) {
                document.getElementById('loadMoreBtn').style.display = 'none';
            }
        })
        .catch(error => {
            console.error("더보기 요청 중 에러 발생:", error);
            alert("수업 더보기 중 오류가 발생했습니다. 콘솔을 확인해주세요.");
        });
});


document.getElementById('resetSearchBtn')?.addEventListener('click', function () {
    // 입력 필드 초기화
    document.getElementById('searchClassDate').value = '';
    document.getElementById('className').value = '';
    document.getElementById('instructor').value = '';

    // 또는 자동으로 검색 폼을 제출해서 초기 조건으로 검색
    document.getElementById('searchForm').submit();
});
