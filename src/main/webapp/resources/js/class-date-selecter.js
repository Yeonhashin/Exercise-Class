document.addEventListener('DOMContentLoaded', function () {
    const prevBtn = document.getElementById('previous-btn');
    const nextBtn = document.getElementById('next-btn');
    const currentStartDate = document.getElementById('currentStartDate').value; // 예: 2024-03-15
    const startDateStr = document.getElementById('current-date').value.trim();
    const today = new Date();
    const startDate = new Date(startDateStr);

    if (startDate <= today) {
        prevBtn.style.display = 'none';
    }

    prevBtn.addEventListener('click', function () {
        moveDate(-5); // 5일 전으로 이동
    });

    nextBtn.addEventListener('click', function () {
        moveDate(5); // 5일 후로 이동
    });

    function moveDate(offset) {
        const date = new Date(currentStartDate);
        date.setDate(date.getDate() + offset); // offset 만큼 이동

        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2);
        const day = ('0' + date.getDate()).slice(-2);

        const newStartDate = `${year}-${month}-${day}`;

        // 페이지 이동 (파라미터로 startDate 전달)
        window.location.href = `/class/list?startDate=${newStartDate}`;
    }
});