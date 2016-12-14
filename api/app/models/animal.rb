class Animal < ApplicationRecord
  belongs_to :user
  has_one :adoption
  validates :name, :categorie, :user, presence: true
end
