json.ignore_nil!
json.extract! animal, :id, :name, :description, :categorie, :created_at

json.user do
  json.partial! animal.user, partial: 'users/user', as: :user
end
if animal.adoption
  json.adoption animal.adoption.id
end
