(function(){

  app.factory('experimentsResource', [
    '$resource',
    'API',
    function($resource, API) {

      var BASE_URL = API.BASE_URL + 'projects/:projectId/experiments';

      return $resource(BASE_URL, {
        projectId: '@projectId'
      }, {
        destroy: {
          url: BASE_URL + '/:experimentId',
          method: 'DELETE',
          params: {
            experimentId: '@experimentId'
          }
        }
      });

  }]);

}());
