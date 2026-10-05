const S={get:(k,d)=>JSON.parse(localStorage.getItem(k)||JSON.stringify(d)),set:(k,v)=>localStorage.setItem(k,JSON.stringify(v))};
function saveProfile(){const p={name:document.querySelector('#name').value||'You',phone:document.querySelector('#phone').value,upi:document.querySelector('#upi').value};S.set('profile',p);location.href='home.html'}
function shareText(t){if(window.AndroidBridge)AndroidBridge.shareText(t);else if(navigator.share)navigator.share({text:t});}
function openUrl(u){if(window.AndroidBridge)AndroidBridge.openUrl(u);else location.href=u}
function pickPhotos(){if(window.AndroidBridge)AndroidBridge.pickPhotos();else document.getElementById('photoInput')?.click()}
window.splyttoPhotosPicked=function(u){S.set('expensePhotos',u); alert(u.length+' photo(s) added')}
function payUPI(app){const p=S.get('profile',{});const upi=p.upi||prompt('Recipient UPI ID');if(!upi)return;const amount=prompt('Amount to pay','1450')||'1450';const base=`upi://pay?pa=${encodeURIComponent(upi)}&pn=Splytto&am=${encodeURIComponent(amount)}&cu=INR&tn=${encodeURIComponent('Splytto settlement')}`;openUrl(base)}
