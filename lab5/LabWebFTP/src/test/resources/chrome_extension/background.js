chrome.webRequest.onAuthRequired.addListener(
  function(details, callbackFn) {
    callbackFn({
      authCredentials: {
        username: "vvss",
        password: "strugure"
      }
    });
  },
  {urls: ["<all_urls>"]},
  ["blocking"]
);
