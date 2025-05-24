/*<![CDATA[*/
var msg = /*[[${msg}]]*/ '';
var status = /*[[${status}]]*/ '';

if (msg && msg.trim() !== '') {
    var iconType = status === 'success' ? 'success' : (status === 'error' ? 'error' : 'info');
    Swal.fire({
        title: '알림',
        text: msg,
        icon: iconType,
        confirmButtonText: '확인'
    });
}
/*]]>*/