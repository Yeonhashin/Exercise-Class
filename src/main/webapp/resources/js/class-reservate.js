document.addEventListener('DOMContentLoaded', function() {
    const reserveButtons = document.querySelectorAll('.open-reserve-modal');
    const cancelButtons = document.querySelectorAll('.open-cancel-modal');

    const modalClassId = document.getElementById('modalClassId');
    const confirmReserveBtn = document.getElementById('confirmReserveBtn');
    const confirmCancelBtn = document.getElementById('confirmCancelBtn');

    // 예약하기 버튼 클릭 시 모달 오픈
    reserveButtons.forEach(button => {
        button.addEventListener('click', function() {
            openModal(this, 'reserve');
        });
    });

    // 예약취소 버튼 클릭 시 모달 오픈
    cancelButtons.forEach(button => {
        button.addEventListener('click', function() {
            openModal(this, 'cancel');
        });
    });

    function openModal(button, actionType) {
        const classId = button.getAttribute('data-class-id');
        const classTypeName = button.getAttribute('data-class-type-name');
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

    // 예약 확정 버튼 클릭 시 폼 제출
    confirmReserveBtn.addEventListener('click', function() {
        document.getElementById('reserveForm').submit();
    });

    // 취소 확정 버튼 클릭 시 AJAX 호출
    confirmCancelBtn.addEventListener('click', function() {
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
                        location.reload(); // 새로고침하여 화면 갱신
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