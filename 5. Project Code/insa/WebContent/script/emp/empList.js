(function(){
	document.addEventListener('DOMCintentLoaded',function(){
		var gridDiv = document.querySelector('#myGrid');
		var gridOptions={};
		new ag.grid.Grid(gridDiv,gridOptions);
		 
	});
})();
