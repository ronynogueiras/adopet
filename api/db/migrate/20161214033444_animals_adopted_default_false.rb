class AnimalsAdoptedDefaultFalse < ActiveRecord::Migration[5.0]
  def change
    change_column :animals, :adopted, :boolean, :default => false
  end
end
