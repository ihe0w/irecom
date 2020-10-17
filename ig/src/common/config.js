let env = 'dev';

let baseDomain = ''


switch (env) {
    case 'dev':
        baseDomain = '/api/';break;
    case 'pro':
        baseDomain = 'http://127.0.0.1:7001/api/';break;
    default:
        baseDomain = 'http://127.0.0.1:7001/api/';
}


export default baseDomain

// switch (env) {
//     case 'dev':
//         baseDomain = '/api/v2';break;
//     case 'pro':
//         baseDomain = 'http://127.0.0.1:7001/api/v2';break;
//     default:
//         baseDomain = 'http://127.0.0.1:7001/api/v2';
// }