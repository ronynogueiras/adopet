json.request do
  json.status 200
end
json.data JSON.parse(yield)
