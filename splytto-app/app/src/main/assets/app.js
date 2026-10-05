const S={get:(k,d)=>{try{return JSON.parse(localStorage.getItem(k)||JSON.stringify(d))}catch(e){return d}},set:(k,v)=>localStorage.setItem(k,JSON.stringify(v))};
function initials(name){const a=(name||'').trim().split(/\s+/).filter(Boolean);if(!a.length)return'?';return a.length===1?a[0].slice(0,2).toUpperCase():(a[0][0]+a[a.length-1][0]).toUpperCase()}
function saveProfile(){const name=document.querySelector('#name').value.trim();if(!name){alert('Please enter your name.');return}const p={name,phone:document.querySelector('#phone').value.trim(),upi:document.querySelector('#upi').value.trim()};S.set('profile',p);location.href='home.html'}
function hydrateProfile(){const p=S.get('profile',{});document.querySelectorAll('[data-avatar]').forEach(x=>x.textContent=initials(p.name));document.querySelectorAll('[data-name]').forEach(x=>x.textContent=p.name||'You')}
function shareText(t){if(window.AndroidBridge)AndroidBridge.shareText(t);else if(navigator.share)navigator.share({text:t});}
function openUrl(u){if(window.AndroidBridge)AndroidBridge.openUrl(u);else location.href=u}
function pickPhotos(){if(window.AndroidBridge)AndroidBridge.pickPhotos();else document.getElementById('photoInput')?.click()}
window.splyttoPhotosPicked=u=>{S.set('expensePhotos',u);alert(u.length+' photo(s) added')}
function payUPI(){const p=S.get('profile',{}),upi=p.upi||prompt('Recipient UPI ID');if(!upi)return;const amount=prompt('Amount to pay','');if(!amount)return;openUrl('upi://pay?pa='+encodeURIComponent(upi)+'&pn=Splytto&am='+encodeURIComponent(amount)+'&cu=INR&tn='+encodeURIComponent('Splytto settlement'))}
document.addEventListener('DOMContentLoaded',hydrateProfile);