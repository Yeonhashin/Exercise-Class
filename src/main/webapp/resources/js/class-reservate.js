document.addEventListener('DOMContentLoaded', function() {
    const reserveButtons = document.querySelectorAll('.open-reserve-modal');
    const cancelButtons = document.querySelectorAll('.open-cancel-modal');

    const modalClassId = document.getElementById('modalClassId');
    const confirmReserveBtn = document.getElementById('confirmReserveBtn');
    const confirmCancelBtn = document.getElementById('confirmCancelBtn');

    document.getElementById('reservationList').addEventListener('click', function (e) {
        const btn = e.target.closest('.open-cancel-modal');
        if (btn) {
            const classId = btn.getAttribute('data-class-id');
            const classTypeName = btn.getAttribute('data-class-type-name');
            const className = btn.getAttribute('data-class-name');
            const instructor = btn.getAttribute('data-class-instructor-name');
            const startTime = btn.getAttribute('data-class-start-time');
            const endTime = btn.getAttribute('data-class-end-time');
            const date = btn.getAttribute('data-class-date');

            // 모달에 데이터 넣기
            document.getElementById('modalClassId').value = classId;
            document.getElementById('modalClassDate').innerText = date;
            document.getElementById('modalClassStartTime').innerText = startTime;
            document.getElementById('modalClassEndTime').innerText = endTime;
            document.getElementById('modalClassTypeName').innerText = classTypeName;
            document.getElementById('modalClassName').innerText = className;
            document.getElementById('modalClassInstructor').innerText = instructor;

            // 버튼 표시 제어
            document.getElementById('confirmReserveBtn').style.display = 'none';
            document.getElementById('confirmCancelBtn').style.display = 'inline-block';

            const modal = new bootstrap.Modal(document.getElementById('reserveModal'));
            modal.show();
        }
    });
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