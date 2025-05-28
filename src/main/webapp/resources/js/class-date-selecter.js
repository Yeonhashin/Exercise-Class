document.addEventListener('DOMContentLoaded', () => {
    const prevBtn = document.getElementById('prev-btn');
    const nextBtn = document.getElementById('next-btn');

    // URL에서 startDate 파라미터 가져오기 (없으면 오늘)
    const urlParams = new URLSearchParams(window.location.search);
    const startDateParam = urlParams.get('startDate');
    let currentDate = startDateParam ? new Date(startDateParam) : new Date();
    currentDate.setHours(0, 0, 0, 0); // 시간 제거

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    function formatDate(date) {
        const y = date.getFullYear();
        const m = ('0' + (date.getMonth() + 1)).slice(-2);
        const d = ('0' + date.getDate()).slice(-2);
        return `${y}-${m}-${d}`;
    }

    function compareDates(date1, date2) {
        const d1 = new Date(date1.getFullYear(), date1.getMonth(), date1.getDate());
        const d2 = new Date(date2.getFullYear(), date2.getMonth(), date2.getDate());
        return d1 - d2;
    }

    function updateUI() {
        if (compareDates(currentDate, today) <= 0) {
            prevBtn.style.display = 'none'; // 오늘보다 과거로 갈 수 없게
        } else {
            prevBtn.style.display = '';
        }
    }

    function moveDate(offsetDays) {
        const newDate = new Date(currentDate);
        newDate.setDate(newDate.getDate() + offsetDays);

        if (newDate < today) {
            return; // 오늘 이전으로는 이동 금지
        }

        const newStartDate = formatDate(newDate);
        window.location.href = `/class/list?startDate=${newStartDate}`;
    }

    prevBtn.addEventListener('click', () => moveDate(-5));
    nextBtn.addEventListener('click', () => moveDate(5));

    updateUI();
});
