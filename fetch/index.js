const helpers = require('../helpers/xmlToJson')
const request = require('request')
const fs = require('fs');

console.log('Start fetching...');

const eventUrl = 'http://api.eventful.com/rest/events/search?app_key=jNBCKZCZtvxkkXLx&category=music&location=leipzig&sort_order=popularity&page_size=1000&date=August';

let eventList = {
    table: []
}

let artistList = {
    table: []
}

let concertList = {
    table: []
}

helpers.xmlToJson(eventUrl, (err, data) => {
    if (err) {
        return console.err(err);
    }

    const events = data.search.events[0].event;
    let performerList = [];

    events.forEach((event) => {
        if(event.performers[0].performer != undefined) {
            event.performers[0].performer.forEach((performer) => {
                const newEvent = {
                    'title': event.title[0],
                    'start_time': event.start_time[0],
                    'venue_name': event.venue_name[0],
                    'artist': performer.name[0],
                } 
                eventList.table.push(newEvent);
                getArtistFromSpotify(performer.name[0])
                .then(() => {
                    let artistJson = JSON.stringify(artistList, null, 2); 
                    fs.writeFile('artists.json', artistJson, 'utf8', () => {
                        console.log('done artists');
                    });
                });
                getUpcomingEvents(performer.name[0]);
            })
        }
    })

    eventList.table.filter((event) => {
        console.log(event.artist);
    })

    let eventsJSON = JSON.stringify(eventList, null, 2);

    fs.writeFile('events.json', eventsJSON, 'utf8', () => {
        console.log('done events');
    });
});

const getArtistFromSpotify = (artistName) => {
    return new Promise((resolve, reject) => {
        request({
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'text/plain',
        
          'Authorization': 'Bearer BQDfINclXfqfs1R_Voc7Be20pBbwfrCtJzgo4hCzX8sEk4bQLu0Hn3nbl7gNiMWtfd77FUMtiOSJs1q88OCG2jWfNVIThiFKEicaiZKcqbU8P0cpj7KitHnLRa3jNQDFvhPouIqXF7W5bbZw'
          },
          uri: 'https://api.spotify.com/v1/search?q=' + artistName + '&type=artist',
          method: 'GET'
        }, function (err, res, body) {
          const parsedBody = JSON.parse(body);
          const artists = parsedBody.artists.items;

          let found = false;
        
          artists.forEach((artist) => {
            if (artist.name.toUpperCase() == artistName.toUpperCase() && !found) {
              const newArtist = {
                'name': artist.name,
                'followers': artist.followers.total,
                'genres': artist.genres,
                'id': artist.id,
              }
              artistList.table.push(newArtist);
              found = true;
            }
          })
    
          if(err) {
            reject(err); return;
          }
    
          resolve();
        });
    })
}

const getUpcomingEvents = (artistName) => {
    return new Promise((resolve, reject) => {
        const uri = 'https://rest.bandsintown.com/artists/' + artistName + '/events?app_id=test_id&date=2018-01-01%2C2019-01-01';
        request(uri, function (error, response, body) {
            const totalConcerts = JSON.parse(body).length;
            const newConcert = {
                'artistName': artistName,
                'totalConcerts': totalConcerts,
            }
            console.log(newConcert)
            concertList.table.push(newConcert);
            let concertJson = JSON.stringify(concertList, null, 2);
                fs.writeFile('concerts.json', concertJson, 'utf8', () => {
                    console.log('done conerts');
                })
        });
        resolve();
    })
}

  