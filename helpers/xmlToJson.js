const parseString = require('xml2js').parseString;
const http = require('http');

exports.xmlToJson = function(url, callback) {
  const req = http.get(url, function(res) {
    let xml = '';
    
    res.on('data', function(chunk) {
      xml += chunk;
    });

    res.on('error', function(e) {
      callback(e, null);
    }); 

    res.on('timeout', function(e) {
      callback(e, null);
    }); 

    res.on('end', function() {
      parseString(xml, function(err, result) {
        callback(null, result);
      });
    });
  });
}