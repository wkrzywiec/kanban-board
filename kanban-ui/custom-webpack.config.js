const webpack = require('webpack');

module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      $ENV: {
        KANBAN_APP_URL: JSON.stringify(process.env.KANBAN_APP_URL)
      }
    })
  ]
};