const io=new IntersectionObserver(entries=>entries.forEach(e=>{if(e.isIntersecting)e.target.classList.add("show")}),{threshold:.12});
document.querySelectorAll(".reveal").forEach(el=>io.observe(el));
const track=document.querySelector(".track");
document.querySelector(".right")?.addEventListener("click",()=>track.scrollBy({left:track.clientWidth*.72,behavior:"smooth"}));
document.querySelector(".left")?.addEventListener("click",()=>track.scrollBy({left:-track.clientWidth*.72,behavior:"smooth"}));
let auto=setInterval(()=>{if(track){const max=track.scrollWidth-track.clientWidth;if(track.scrollLeft>=max-10)track.scrollTo({left:0,behavior:"smooth"});else track.scrollBy({left:track.clientWidth*.5,behavior:"smooth"})}},4500);
track?.addEventListener("mouseenter",()=>clearInterval(auto));
document.addEventListener("mousemove",e=>{document.documentElement.style.setProperty("--mx",e.clientX+"px");document.documentElement.style.setProperty("--my",e.clientY+"px")});
