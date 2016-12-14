json.ignore_nil!
json.extract! adoption, :created_at

json.new_owner do
  json.partial! adoption.new_owner, partial: 'users/user', as: :user
end

json.old_owner do
  json.partial! adoption.old_owner, partial: 'users/user', as: :user
end

json.animal do
  json.partial! adoption.animal, partial: 'animals/animal', as: :animal
end
