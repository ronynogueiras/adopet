class Adoption < ApplicationRecord
  belongs_to :animal
  belongs_to :old_owner, class_name: "User"
  belongs_to :new_owner, class_name: "User"

  validates :animal, :old_owner, :new_owner, presence: true
  validate :different_users?

  private

  def different_users?
    return if old_owner.id != new_owner.id
    errors.add(:same_user, 'Usuários não podem ser os mesmos !')
  end
end
