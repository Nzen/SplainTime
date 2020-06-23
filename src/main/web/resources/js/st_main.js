
/* Splaintime copyright 2020 Nicholas Prado;
	released under Fair license terms */


var std = function() {};
std.log = function( message )
{
	try
	{ console.log( message ); }
	catch ( exception )
	{ return; } // IE reputedly has no console.
}


window.onload = function()
{
	const st_input = document.getElementById( 'sti_input' );
	st_input.addEventListener(
		'keydown',
		( ev ) => {
			if ( ev.key === 'Enter' ) {
				sendInput( st_input.value );
			}
		}
	);
};


function sendInput( someText ) {
	alert( someText );
}




























