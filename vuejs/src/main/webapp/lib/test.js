function my(content, a = 0) {
	console.log("my first:" + content + a);
}

function my(content) {
	console.log("my second:" + content);
}

my();
my('hihi');