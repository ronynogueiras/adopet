FactoryGirl.define do
  factory :user do
    name 'CompanyName'
    email { Faker::Internet.email }
    password 'password'
  end
end
