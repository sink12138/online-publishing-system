using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ArticleManager
{
    public partial class AddReviewerForm : Form
    {
        SqlConnection Sc;
        SqlDataAdapter Sda;
        DataSet Ds;
        DataTable Dt;

        public AddReviewerForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            button1.Enabled = false;
            string name = textBox2.Text;
            string email = textBox4.Text;
            if (name.Length == 0 || email.Length == 0)
            {
                MessageBox.Show("信息不能为空！");
                button1.Enabled = true;
                return;
            }
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from Reviewer", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("insert into reviewer(name,email) values(@name,@email)", Sc);
            scmd.Parameters.Add(new SqlParameter("@name", SqlDbType.VarChar, 80, "name"));
            scmd.Parameters.Add(new SqlParameter("@email", SqlDbType.VarChar, 100, "email"));
            Sda.InsertCommand = scmd;
            DataRow dr = Dt.NewRow();
            try
            {
                dr["name"] = name;
                dr["email"] = email;
                Dt.Rows.Add(dr);
                Sda.Update(Ds);
            }
            catch { };
            MessageBox.Show("添加成功！");
            button1.Enabled = true;
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void AddReviewerForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }
    }
}
