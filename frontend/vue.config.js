const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  lintOnSave: false,
  transpileDependencies: ["vuetify"],
  outputDir: "../src/main/resources/static", // Build Directory
  devServer: {
    proxy: {
      "/board": {
        target: "http://localhost:8081", // Spring Boot Server
        changeOrigin: true,
      },
      "/user": {
        target: "http://localhost:8081", // Spring Boot Server
        changeOrigin: true,
      },
      "/comment": {
        target: "http://localhost:8081", // Spring Boot Server
        changeOrigin: true,
      },
    },
  },
});
