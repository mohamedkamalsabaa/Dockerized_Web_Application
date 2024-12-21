Vagrant.configure("2") do |config|
  config.vm.define "machine1" do |machine1|
    machine1.vm.box = "ubuntu/bionic64"
    machine1.vm.network "private_network", type: "dhcp"  # DHCP instead of static IP
    machine1.vm.synced_folder "/mnt/c/Users/Mohamed/vagrant-final-project", "/vagrant", type: "virtualbox"
  end

  config.vm.define "machine2" do |machine2|
    machine2.vm.box = "ubuntu/bionic64"
    machine2.vm.network "private_network", type: "dhcp"  # DHCP instead of static IP
    machine2.vm.synced_folder "/mnt/c/Users/Mohamed/vagrant-final-project", "/vagrant", type: "virtualbox"
  end
end
